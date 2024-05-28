package org.example.conectatec.utecServices.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.example.conectatec.utecServices.dto.UtecServicesDto;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.dto.UtecServicesFeedDto;
import org.example.conectatec.utecServicesFeed.infrastructure.UtecServicesFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtecServicesService {
    private final UtecServicesRepository utecServicesRepository;
    private final UtecServicesFeedRepository utecServicesFeedRepository;
    private final AuthorizationUtils authorizationUtils;
    @Autowired
    public UtecServicesService(UtecServicesRepository utecServicesRepository,UtecServicesFeedRepository utecServicesFeedRepository, AuthorizationUtils authorizationUtils) {
        this.utecServicesRepository = utecServicesRepository;
        this.utecServicesFeedRepository = utecServicesFeedRepository;
        this.authorizationUtils = authorizationUtils;
    }

    @Transactional
    public UtecServices saveUtecServices(UtecServices sutec) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return utecServicesRepository.save(sutec);
    }

    @Transactional
    public UtecServicesDto findUtecServicesInfo(Long id) {
         UtecServices utecServices= utecServicesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("UTEC account not found"));
        return mapToDto(utecServices);
    }

    @Transactional
    public UtecServices findUtecServicesByEmail(String email) {

        return utecServicesRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Transactional
    public void deleteUtecServices_porId(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        utecServicesRepository.deleteById(id);
    }

    @Transactional
    public List<UtecServices> findAllUtecServices() {
        return utecServicesRepository.findAll();
    }

    @Transactional
    public UtecServices updateUtecServices(Long id, UtecServices updatedUtecServices) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServices existingUtecServices = utecServicesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("UTEC account not found"));
        if (existingUtecServices != null) {
            UtecServices sutec = existingUtecServices;
            sutec.setName(updatedUtecServices.getName());
            sutec.setEmail(updatedUtecServices.getEmail());
            sutec.setPassword(updatedUtecServices.getPassword());
            return utecServicesRepository.save(sutec);
        } else {
            return null;
        }
    }
    private UtecServicesDto mapToDto(UtecServices utecServices) {
        UtecServicesDto response = new UtecServicesDto();
        response.setId(utecServices.getId());
        response.setName(utecServices.getName());
        response.setEmail(utecServices.getEmail());

        UtecServicesFeed utecServicesFeed = utecServices.getUtecServicesFeed();
        if (utecServicesFeed != null) {
            UtecServicesFeedDto utecServicesFeedDto = new UtecServicesFeedDto();
            utecServicesFeedDto.setId(utecServicesFeed.getId());
            utecServicesFeedDto.setCaption(utecServicesFeed.getCaption());
            utecServicesFeedDto.setMedia(utecServicesFeed.getMedia());
            response.setUtecServicesFeed(utecServicesFeedDto.getUtecServicesFeed());
        }

        return response;
    }

}

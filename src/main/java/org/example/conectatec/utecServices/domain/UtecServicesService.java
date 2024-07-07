package org.example.conectatec.utecServices.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.example.conectatec.utecServices.dto.UtecServicesDto;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.example.conectatec.utecServicesPost.dto.UtecServicesPostDto;
import org.example.conectatec.utecServicesPost.infrastructure.UtecServicesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtecServicesService {

    private final UtecServicesRepository utecServicesRepository;
    private final UtecServicesPostRepository utecServicesPostRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public UtecServicesService(UtecServicesRepository utecServicesRepository, UtecServicesPostRepository utecServicesPostRepository, AuthorizationUtils authorizationUtils) {
        this.utecServicesRepository = utecServicesRepository;
        this.utecServicesPostRepository = utecServicesPostRepository;
        this.authorizationUtils = authorizationUtils;
    }

    @Transactional
    public UtecServices saveUtecServices(UtecServices utecServices) {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return utecServicesRepository.save(utecServices);
    }

    @Transactional
    public UtecServicesDto findUtecServicesInfo(Long id) {
        UtecServices utecServices = utecServicesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UTEC account not found"));
        return mapToDto(utecServices);
    }

    @Transactional
    public UtecServices findUtecServicesByEmail(String email) {
        return utecServicesRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("UTEC account not found"));
    }

    @Transactional
    public void deleteUtecServicesPorId(Long id) {
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
        UtecServices existingUtecServices = utecServicesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UTEC account not found"));
        existingUtecServices.setName(updatedUtecServices.getName());
        existingUtecServices.setEmail(updatedUtecServices.getEmail());
        existingUtecServices.setPassword(updatedUtecServices.getPassword());
        return utecServicesRepository.save(existingUtecServices);
    }

    @Transactional
    public UtecServices partialUpdateUtecServices(Long id, UtecServices partialUtecServices) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServices existingUtecServices = utecServicesRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UTEC account not found"));

        if (partialUtecServices.getName() != null) {
            existingUtecServices.setName(partialUtecServices.getName());
        }
        if (partialUtecServices.getEmail() != null) {
            existingUtecServices.setEmail(partialUtecServices.getEmail());
        }
        if (partialUtecServices.getPassword() != null) {
            existingUtecServices.setPassword(partialUtecServices.getPassword());
        }

        return utecServicesRepository.save(existingUtecServices);
    }

    private UtecServicesDto mapToDto(UtecServices utecServices) {
        UtecServicesDto response = new UtecServicesDto();
        response.setId(utecServices.getId());
        response.setName(utecServices.getName());
        response.setEmail(utecServices.getEmail());

        UtecServicesPost utecServicesPost = utecServices.getUtecServicesPost();
        if (utecServicesPost != null) {
            UtecServicesPostDto utecServicesPostDto = new UtecServicesPostDto();
            utecServicesPostDto.setId(utecServicesPost.getId());
            utecServicesPostDto.setCaption(utecServicesPost.getCaption());
            utecServicesPostDto.setMedia(utecServicesPost.getMedia());
            response.setUtecServicesPost(utecServicesPostDto.getUtecServicesPost());
        }

        return response;
    }

}

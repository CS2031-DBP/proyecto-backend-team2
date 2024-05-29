package org.example.conectatec.utecServices.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.example.conectatec.utecServices.dto.UtecServicesDto;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.dto.UtecServicesFeedDto;
import org.example.conectatec.utecServicesFeed.infrastructure.UtecServicesFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtecServicesService {

    private final UtecServicesRepository utecServicesRepository;
    private final UtecServicesFeedRepository utecServicesFeedRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public UtecServicesService(UtecServicesRepository utecServicesRepository, UtecServicesFeedRepository utecServicesFeedRepository, AuthorizationUtils authorizationUtils) {
        this.utecServicesRepository = utecServicesRepository;
        this.utecServicesFeedRepository = utecServicesFeedRepository;
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

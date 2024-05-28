package org.example.conectatec.utecServices.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.example.conectatec.utecServicesFeed.infrastructure.UtecServicesFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtecServicesService {
    @Autowired
    private UtecServicesRepository utecServicesRepository;

    @Autowired
    private UtecServicesFeedRepository utecServicesFeedRepository;

    @Transactional
    public UtecServices saveUtecServices(UtecServices sutec) {
        return utecServicesRepository.save(sutec);
    }

    @Transactional
    public UtecServices findUtecServicesById(Long id) {
        return utecServicesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("UTEC account not found"));
    }

    @Transactional
    public void deleteUtecServices_porId(Long id) {
        utecServicesRepository.deleteById(id);
    }

    @Transactional
    public List<UtecServices> findAllUtecServices() {
        return utecServicesRepository.findAll();
    }

    @Transactional
    public UtecServices updateUtecServices(Long id, UtecServices updatedUtecServices) {
        Optional<UtecServices> existingUtecServices = utecServicesRepository.findById(id);
        if (existingUtecServices.isPresent()) {
            UtecServices sutec = existingUtecServices.get();
            sutec.setName(updatedUtecServices.getName());
            sutec.setEmail(updatedUtecServices.getEmail());
            sutec.setPassword(updatedUtecServices.getPassword());
            return utecServicesRepository.save(sutec);
        } else {
            return null;
        }
    }

    @Transactional
    public UtecServicesFeed addPublicationToUtecService(Long utecServiceId, UtecServicesFeed publication) {
        Optional<UtecServices> sutecOptional = utecServicesRepository.findById(utecServiceId);
        if (sutecOptional.isPresent()) {
            UtecServices sutec = sutecOptional.get();
            publication.setServicesUTEC(sutec);
            sutec.getPublications().add(publication);
            utecServicesRepository.save(sutec);
            return publication;
        } else {
            return null;
        }
    }

    @Transactional
    public List<UtecServicesFeed> findPublicationsByHashtag(String hashtag) {
        return utecServicesFeedRepository.findByHashtag(hashtag);
    }

}

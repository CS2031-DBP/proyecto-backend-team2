package org.example.conectatec.utecServicesFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.example.conectatec.utecServicesFeed.infrastructure.UtecServicesFeedRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtecServicesFeedService {


    private final UtecServicesFeedRepository utecServicesFeedRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    @Autowired
    public UtecServicesFeedService(AuthorizationUtils authorizationUtils, UtecServicesFeedRepository utecServicesFeedRepository, ModelMapper modelMapper) {
        this.authorizationUtils = authorizationUtils;
        this.utecServicesFeedRepository = utecServicesFeedRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UtecServicesFeed saveUtecServicesPublications(UtecServicesFeed utecServicesFeed) {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return utecServicesFeedRepository.save(utecServicesFeed);
    }

    @Transactional
    public List<UtecServicesFeed> findUtecServicesPublicationsByHashtag(String hashtag) {
        return utecServicesFeedRepository.findByHashtag(hashtag);
    }

    @Transactional
    public void deleteUtecServicesPublication(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        utecServicesFeedRepository.deleteById(id);
    }

    @Transactional
    public List<UtecServicesFeed> findAllPublications() {
        return utecServicesFeedRepository.findAll();
    }

    @Transactional
    public UtecServicesFeed updateUtecServicesFeed(Long id, UtecServicesFeed updatedUtecServicesFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServicesFeed existingFeed = utecServicesFeedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UtecServicesFeed not found"));

        modelMapper.map(updatedUtecServicesFeed, existingFeed);
        return utecServicesFeedRepository.save(existingFeed);
    }

    @Transactional
    public UtecServicesFeed partialUpdateUtecServicesFeed(Long id, UtecServicesFeed partialUtecServicesFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServicesFeed existingFeed = utecServicesFeedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UtecServicesFeed not found"));

        if (partialUtecServicesFeed.getHashtag() != null) {
            existingFeed.setHashtag(partialUtecServicesFeed.getHashtag());
        }
        if (partialUtecServicesFeed.getMedia() != null) {
            existingFeed.setMedia(partialUtecServicesFeed.getMedia());
        }
        if (partialUtecServicesFeed.getCaption() != null) {
            existingFeed.setCaption(partialUtecServicesFeed.getCaption());
        }

        return utecServicesFeedRepository.save(existingFeed);
    }

}

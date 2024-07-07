package org.example.conectatec.utecServicesPost.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.example.conectatec.utecServicesPost.infrastructure.UtecServicesPostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UtecServicesPostService {


    private final UtecServicesPostRepository utecServicesPostRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    @Autowired
    public UtecServicesPostService(AuthorizationUtils authorizationUtils, UtecServicesPostRepository utecServicesFeedRepository, ModelMapper modelMapper) {
        this.authorizationUtils = authorizationUtils;
        this.utecServicesPostRepository = utecServicesFeedRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public UtecServicesPost saveUtecServicesPublications(UtecServicesPost utecServicesFeed) {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return utecServicesPostRepository.save(utecServicesFeed);
    }

    @Transactional
    public List<UtecServicesPost> findUtecServicesPublicationsByHashtag(String hashtag) {
        return utecServicesPostRepository.findByHashtag(hashtag);
    }

    @Transactional
    public void deleteUtecServicesPublication(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        utecServicesPostRepository.deleteById(id);
    }

    @Transactional
    public List<UtecServicesPost> findAllPublications() {
        return utecServicesPostRepository.findAll();
    }

    @Transactional
    public UtecServicesPost updateUtecServicesPost(Long id, UtecServicesPost updatedUtecServicesFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServicesPost existingFeed = utecServicesPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UtecServicesFeed not found"));

        modelMapper.map(updatedUtecServicesFeed, existingFeed);
        return utecServicesPostRepository.save(existingFeed);
    }

    @Transactional
    public UtecServicesPost partialUpdateUtecServicesPost(Long id, UtecServicesPost partialUtecServicesFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        UtecServicesPost existingFeed = utecServicesPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UtecServicesFeed not found"));

        if (partialUtecServicesFeed.getHashtag() != null) {
            existingFeed.setHashtag(partialUtecServicesFeed.getHashtag());
        }
        if (partialUtecServicesFeed.getMedia() != null) {
            existingFeed.setMedia(partialUtecServicesFeed.getMedia());
        }
        if (partialUtecServicesFeed.getCaption() != null) {
            existingFeed.setCaption(partialUtecServicesFeed.getCaption());
        }

        return utecServicesPostRepository.save(existingFeed);
    }

}

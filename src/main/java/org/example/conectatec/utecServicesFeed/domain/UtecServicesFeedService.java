package org.example.conectatec.utecServicesFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServicesFeed.infrastructure.UtecServicesFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class UtecServicesFeedService {

    @Autowired
    private UtecServicesFeedRepository sutecfeedrepository;

    @Transactional
    public UtecServicesFeed saveUtecServicesPublications(UtecServicesFeed servicesUTECPublications) {
        return sutecfeedrepository.save(servicesUTECPublications);
    }

    @Transactional
    public Optional<UtecServicesFeed> findUtecServicesPublicationsById(Long id) {
        return sutecfeedrepository.findById(id);
    }

    @Transactional
    public List<UtecServicesFeed> findPublicationsByUtecServices(UtecServices sutec) {
        return sutecfeedrepository.findByServicesUTEC(sutec);
    }

    @Transactional
    public List<UtecServicesFeed> findUtecServicesPublicationsByHashtag(String hashtag) {
        return sutecfeedrepository.findByHashtag(hashtag);
    }

    @Transactional
    public void deleteUtecServicesPublication(Long id) {
        sutecfeedrepository.deleteById(id);
    }

    @Transactional
    public List<UtecServicesFeed> findAllPublications() {
        return sutecfeedrepository.findAll();
    }

}

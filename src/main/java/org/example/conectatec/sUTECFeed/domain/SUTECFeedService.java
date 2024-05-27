package org.example.conectatec.sUTECFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.sUTEC.domain.SUTEC;
import org.example.conectatec.sUTECFeed.infrastructure.SUTECFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class SUTECFeedService {

    @Autowired
    private SUTECFeedRepository sutecfeedrepository;

    @Transactional
    public SUTECFeed saveUtecServicesPublications(SUTECFeed servicesUTECPublications) {
        return sutecfeedrepository.save(servicesUTECPublications);
    }

    @Transactional
    public Optional<SUTECFeed> findUtecServicesPublicationsById(Long id) {
        return sutecfeedrepository.findById(id);
    }

    @Transactional
    public List<SUTECFeed> findPublicationsByUtecServices(SUTEC sutec) {
        return sutecfeedrepository.findByServicesUTEC(sutec);
    }

    @Transactional
    public void deleteUtecServicesPublication(Long id) {
        sutecfeedrepository.deleteById(id);
    }

    @Transactional
    public List<SUTECFeed> findAllPublications() {
        return sutecfeedrepository.findAll();
    }

}

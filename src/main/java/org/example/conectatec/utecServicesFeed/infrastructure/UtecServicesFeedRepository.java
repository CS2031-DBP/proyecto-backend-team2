package org.example.conectatec.utecServicesFeed.infrastructure;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtecServicesFeedRepository extends JpaRepository<UtecServicesFeed, Long> {
    List<UtecServicesFeed> findByServicesUTEC(UtecServices sutec);

    List<UtecServicesFeed> findByHashtag(String hashtag);

}

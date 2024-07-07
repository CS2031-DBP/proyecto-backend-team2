package org.example.conectatec.utecServicesPost.infrastructure;

import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UtecServicesPostRepository extends JpaRepository<UtecServicesPost, Long> {
    List<UtecServicesPost> findByServicesUTEC(UtecServices sutec);
    List<UtecServicesPost> findByHashtag(String hashtag);


}

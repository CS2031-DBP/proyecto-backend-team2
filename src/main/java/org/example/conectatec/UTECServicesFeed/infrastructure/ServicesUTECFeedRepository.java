package org.example.conectatec.UTECServicesFeed.infrastructure;

import org.example.conectatec.UTECServicesFeed.domain.ServicesUTECFeed;
import org.example.conectatec.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesUTECFeedRepository extends JpaRepository<ServicesUTECFeed, Long> {
    List<ServicesUTECFeed> findByServicesUTEC(Student student);
}

package org.example.conectatec.sUTECFeed.infrastructure;

import org.example.conectatec.sUTECFeed.domain.ServicesUTECFeed;
import org.example.conectatec.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicesUTECFeedRepository extends JpaRepository<ServicesUTECFeed, Long> {
    List<ServicesUTECFeed> findByServicesUTEC(Student student);
}

package org.example.conectatec.sUTECFeed.infrastructure;

import org.example.conectatec.sUTECFeed.domain.SUTECFeed;
import org.example.conectatec.student.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SUTECFeedRepository extends JpaRepository<SUTECFeed, Long> {
    List<SUTECFeed> findByServicesUTEC(Student student);
}

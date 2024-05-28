package org.example.conectatec.studentFeed.infrastructure;

import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentFeedRepository extends JpaRepository<StudentFeed, Long> {
    List<StudentFeed> findByStudent(Student student);
}

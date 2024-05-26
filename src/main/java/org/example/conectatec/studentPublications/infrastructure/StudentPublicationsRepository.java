package org.example.conectatec.studentPublications.infrastructure;

import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentPublicationsRepository extends JpaRepository<StudentPublications, Long> {
    List<StudentPublications> findByStudent(Student student);
}

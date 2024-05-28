package org.example.conectatec.student.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface StudentRepository extends UserBaseRepository <Student> {

    List<Student> findByCareer(Career career);


}

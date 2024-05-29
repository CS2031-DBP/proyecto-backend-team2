package org.example.conectatec.student.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository

public interface StudentRepository extends JpaRepository<Student,Long> {

    List<Student> findByCareer(Career career);
    Optional<Student> findByEmail(String email);

}

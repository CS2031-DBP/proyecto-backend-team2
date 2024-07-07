package org.example.conectatec.studentPost.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentPostRepository extends JpaRepository<StudentPost, Long> {
    List<StudentPost> findByStudent(Student student);

    @Query("SELECT sf FROM StudentPost sf JOIN sf.student s WHERE s.career = :career")
    Optional<StudentPost> findByCareer(@Param("career") Career career);

    Optional<StudentPost> findByHashtag(String hashtag);
}

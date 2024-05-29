package org.example.conectatec.studentFeed.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentFeedRepository extends JpaRepository<StudentFeed, Long> {
    List<StudentFeed> findByStudent(Student student);

    @Query("SELECT sf FROM StudentFeed sf JOIN sf.student s WHERE s.career = :career")
    Optional<StudentFeed> findByCareer(@Param("career") Career career);
}

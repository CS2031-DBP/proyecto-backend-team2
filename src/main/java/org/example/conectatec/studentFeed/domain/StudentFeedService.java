package org.example.conectatec.studentFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentFeed.infrastructure.StudentFeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentFeedService {

    @Autowired
    StudentFeedRepository studentFeedRepository;

    @Transactional
    public StudentFeed saveStudentPublication(StudentFeed studentPublication) {
        return studentFeedRepository.save(studentPublication);
    }

    @Transactional
    public StudentFeed findStudentPublicationById(Long id) {
        return studentFeedRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Student publication with id " + id + " not found"));
    }

    @Transactional
    public List<StudentFeed> findPublicationsByStudent(Student student) {
        return studentFeedRepository.findByStudent(student);
    }

    @Transactional
    public void deleteStudentPublication(Long id) {
        studentFeedRepository.deleteById(id);
    }

    @Transactional
    public List<StudentFeed> findAllPublications() {
        return studentFeedRepository.findAll();
    }
}

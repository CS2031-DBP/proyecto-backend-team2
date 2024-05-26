package org.example.conectatec.studentPublications.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.infrastructure.StudentPublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentPublicationsService {

    @Autowired
    StudentPublicationsRepository studentPublicationsRepository;

    @Transactional
    public StudentPublications saveStudentPublication(StudentPublications studentPublication) {
        return studentPublicationsRepository.save(studentPublication);
    }

    @Transactional
    public Optional<StudentPublications> findStudentPublicationById(Long id) {
        return studentPublicationsRepository.findById(id);
    }

    @Transactional
    public List<StudentPublications> findPublicationsByStudent(Student student) {
        return studentPublicationsRepository.findByStudent(student);
    }

    @Transactional
    public void deleteStudentPublication(Long id) {
        studentPublicationsRepository.deleteById(id);
    }

    @Transactional
    public List<StudentPublications> findAllPublications() {
        return studentPublicationsRepository.findAll();
    }
}

package org.example.conectatec.student.domain;


import jakarta.transaction.Transactional;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Transactional
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Transactional
    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Transactional
    public void deleteStudent_porId(Long id) {
        studentRepository.deleteById(id);
    }

    @Transactional
    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional
    public List<Student> findStudentsByCareer(Career career) {
        return studentRepository.findByCareer(career);
    }


    public List<Student> findStudentsByCareerName(String careerName) {
        Optional<Career> career = careerRepository.findByName(careerName);
        if (career.isPresent()) {
            return studentRepository.findByCareer(career.get());
        } else {
            return Collections.emptyList();
        }
    }

}

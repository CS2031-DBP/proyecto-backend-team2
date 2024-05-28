package org.example.conectatec.student.domain;


import jakarta.transaction.Transactional;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AuthorizationUtils authorizationUtils;


    @Transactional
    public Student saveStudent(Student student) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return studentRepository.save(student);
    }

    @Transactional
    public StudentDto findStudentInfo(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return mapToDto(student);
    }
    @Transactional
    public Student findStudentByEmail(String email) {

        return studentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Student student= studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        studentRepository.delete(student);
    }

    @Transactional
    public List<Student> findAllStudents() {

        return studentRepository.findAll();
    }

    @Transactional
    public List<Student> findStudentsByCareer(Career career) {

        return studentRepository.findByCareer(career);
    }

    private StudentDto mapToDto(Student student) {
        StudentDto response = new StudentDto();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        Career career = student.getCareer();
        if (career != null) {
            CareerDto careerDto = new CareerDto();
            careerDto.setId(career.getId());
            careerDto.setName(career.getName());
            careerDto.setFacultad(career.getFacultad());
            response.setCareer(careerDto);
        }
        StudentFeed studentFeed = student.getStudentFeed();
        if (studentFeed != null) {
            StudentFeedDto studentFeedDto = new StudentFeedDto();
            studentFeedDto.setId(studentFeed.getId());
            studentFeedDto.setCaption(studentFeed.getCaption());
            studentFeedDto.setMedia(studentFeed.getMedia());
            response.setStudentFeed(studentFeedDto);
        }

        return response;
    }

}

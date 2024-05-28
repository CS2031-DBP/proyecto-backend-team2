package org.example.conectatec.student.application;

import jakarta.validation.constraints.Email;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.EmailService;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.domain.StudentService;
import org.example.conectatec.student.dto.StudentDto;
import org.example.conectatec.student.events.HelloEmailEvent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable Long id) {
        StudentDto student = studentService.findStudentInfo(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentByEmail(@PathVariable String email) {
        studentService.findStudentByEmail(email);
        StudentDto studentDto = modelMapper.map(studentService.findStudentByEmail(email), StudentDto.class);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.saveStudent(student);
        StudentDto studentDto = modelMapper.map(newStudent, StudentDto.class);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/career")
    public ResponseEntity<List<Student>> getStudentsByCareer(@RequestParam Long careerId) {
        Career career = new Career();
        career.setId(careerId);
        List<Student> students = studentService.findStudentsByCareer(career);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/verificacion")
    public ResponseEntity<String> sendEmail(@RequestParam String email) {
        applicationEventPublisher.publishEvent(new HelloEmailEvent(email));
        return ResponseEntity.ok("¡Hola mundo!");
    }

}

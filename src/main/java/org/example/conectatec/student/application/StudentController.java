package org.example.conectatec.student.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.domain.StudentService;
import org.example.conectatec.student.dto.StudentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.findStudentById(id);
        StudentDto studentDto = modelMapper.map(student, StudentDto.class);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.saveStudent(student);
        StudentDto studentDto = modelMapper.map(newStudent, StudentDto.class);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent_porId(@PathVariable Long id) {
        studentService.deleteStudent_porId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/career")
    public ResponseEntity<List<Student>> getStudentsByCareer(@RequestParam Long careerId) {
        Career career = new Career();
        career.setId(careerId); // Id de carrera si es que se puede usar un boton para dar id.

        List<Student> students = studentService.findStudentsByCareer(career);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/by-career")
    public ResponseEntity<List<Student>> getStudentsByCareerName(@RequestParam String careerName) {
        List<Student> students = studentService.findStudentsByCareerName(careerName);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}

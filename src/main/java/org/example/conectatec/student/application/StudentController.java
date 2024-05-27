package org.example.conectatec.student.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.domain.StudentService;
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


    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.findStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student newStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
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

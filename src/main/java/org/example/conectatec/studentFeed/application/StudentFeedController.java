package org.example.conectatec.studentFeed.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.studentFeed.domain.StudentFeedService;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/student-publications")
public class StudentFeedController {


    private final StudentFeedService studentFeedService;
    private final ModelMapper modelMapper;
    @Autowired
    public StudentFeedController(StudentFeedService studentFeedService, ModelMapper modelMapper) {
        this.studentFeedService = studentFeedService;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<StudentFeedDto>> findAllStudentPosts() {
        List<StudentFeed> studentPosts = studentFeedService.findAllPublications();
        Type ListType = new TypeToken<List<StudentFeedDto>>() {}.getType();
        List<StudentFeedDto> studentPostsDto = modelMapper.map(studentPosts, ListType);
        return new ResponseEntity<>(studentPostsDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/career")
    public ResponseEntity<StudentFeedDto> getStudentPublicationByCareer(@RequestParam Career career) {
        StudentFeed publicationByCareer = studentFeedService.findStudentPublicationByCareer(career);
        StudentFeedDto studentFeedDto = modelMapper.map(publicationByCareer, StudentFeedDto.class);
        return new ResponseEntity<>(studentFeedDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<StudentFeedDto> createStudentPost(@RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed studentPublication = modelMapper.map(studentFeedDto, StudentFeed.class);
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentFeed> updateStudentFeed(@PathVariable Long id, @RequestBody StudentFeed updatedStudentFeed) {
        StudentFeed updatedFeed = studentFeedService.updateStudentPublication(id, updatedStudentFeed);
        if (updatedFeed != null) {
            return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentFeed> partialUpdateStudentFeed(@PathVariable Long id, @RequestBody StudentFeed partialStudentFeed) {
        StudentFeed updatedFeed = studentFeedService.partialUpdateStudentPublication(id, partialStudentFeed);
        if (updatedFeed != null) {
            return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentFeed(@PathVariable Long id) {
        studentFeedService.deleteStudentPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

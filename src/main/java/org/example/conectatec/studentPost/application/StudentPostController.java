package org.example.conectatec.studentPost.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.example.conectatec.studentPost.domain.StudentPostService;
import org.example.conectatec.studentPost.dto.StudentPostDto;
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
public class StudentPostController {


    private final StudentPostService studentPostService;
    private final ModelMapper modelMapper;
    @Autowired
    public StudentPostController(StudentPostService studentPostService, ModelMapper modelMapper) {
        this.studentPostService = studentPostService;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<StudentPostDto>> findAllStudentPosts() {
        List<StudentPost> studentPosts = studentPostService.findAllPublications();
        Type ListType = new TypeToken<List<StudentPostDto>>() {}.getType();
        List<StudentPostDto> studentPostsDto = modelMapper.map(studentPosts, ListType);
        return new ResponseEntity<>(studentPostsDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/career")
    public ResponseEntity<StudentPostDto> getStudentPublicationByCareer(@RequestParam Career career) {
        StudentPost publicationByCareer = studentPostService.findStudentPublicationByCareer(career);
        StudentPostDto studentFeedDto = modelMapper.map(publicationByCareer, StudentPostDto.class);
        return new ResponseEntity<>(studentFeedDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<StudentPostDto> createStudentPost(@RequestBody StudentPostDto studentPostDto) {
        StudentPost studentPublication = modelMapper.map(studentPostDto, StudentPost.class);
        studentPostService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentPostDto.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentPost> updateStudentFeed(@PathVariable Long id, @RequestBody StudentPost updatedStudentPost) {
        StudentPost updatedPost = studentPostService.updateStudentPublication(id, updatedStudentPost);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentPost> partialUpdateStudentFeed(@PathVariable Long id, @RequestBody StudentPost partialStudentFeed) {
        StudentPost updatedFeed = studentPostService.partialUpdateStudentPublication(id, partialStudentFeed);
        if (updatedFeed != null) {
            return new ResponseEntity<>(updatedFeed, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentFeed(@PathVariable Long id) {
        studentPostService.deleteStudentPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Nuevo endpoint para obtener el ID por hashtag
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/id-by-hashtag")
    public ResponseEntity<Long> getStudentFeedIdByHashtag(@RequestParam String hashtag) {
        Long id = studentPostService.findStudentFeedIdByHashtag(hashtag);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


}

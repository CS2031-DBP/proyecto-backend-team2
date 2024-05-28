package org.example.conectatec.studentFeed.application;

import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.studentFeed.domain.StudentFeedService;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.example.conectatec.studentFeed.infrastructure.StudentFeedRepository;
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
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<StudentFeedDto> createStudentPost(@RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed studentPublication = modelMapper.map(studentFeedDto, StudentFeed.class);
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<StudentFeedDto> updateStudentPost(@PathVariable Long id, @RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed existingPost = studentFeedService.findById(id);
        if (existingPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StudentFeed studentPublication = modelMapper.map(studentFeedDto, StudentFeed.class);
        studentPublication.setId(id);
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @PatchMapping("/{id}")
    public ResponseEntity<StudentFeedDto> partialUpdateStudentPost(@PathVariable Long id, @RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed existingPost = studentFeedService.findById(id);
        if (existingPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        StudentFeed studentPublication = existingPost.getStudent().getStudentFeed();
        if (studentFeedDto.getHashtag() != null) {
            studentPublication.setHashtag(studentFeedDto.getHashtag());
        }
        if (studentFeedDto.getMedia() != null) {
            studentPublication.setMedia(studentFeedDto.getMedia());
        }
        if (studentFeedDto.getCommentBox() != null) {
            studentPublication.setComments(studentFeedDto.getCommentBox());
        }
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentPost(@PathVariable Long id) {
        StudentFeed existingPost = studentFeedService.findById(id);
        if (existingPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentFeedService.deleteStudentPublication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

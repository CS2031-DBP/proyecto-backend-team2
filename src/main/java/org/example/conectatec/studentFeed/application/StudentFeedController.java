package org.example.conectatec.studentFeed.application;

import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.studentFeed.domain.StudentFeedService;
import org.example.conectatec.studentFeed.dto.StudentFeedDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/student-publications")
public class StudentFeedController {

    @Autowired
    private StudentFeedService studentFeedService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<StudentFeedDto> findStudentPostById(@PathVariable Long id) {
        StudentFeed studentPublication = studentFeedService.findStudentPublicationById(id);
        StudentFeedDto studentFeedDto = modelMapper.map(studentPublication, StudentFeedDto.class);
        return new ResponseEntity<>(studentFeedDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentFeedDto>> findAllStudentPosts() {
        List<StudentFeed> studentPosts = studentFeedService.findAllPublications();
        Type ListType = new TypeToken<List<StudentFeedDto>>() {}.getType();
        List<StudentFeedDto> studentPostsDto = modelMapper.map(studentPosts, ListType);
        return new ResponseEntity<>(studentPostsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentFeedDto> createStudentPost(@RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed studentPublication = modelMapper.map(studentFeedDto, StudentFeed.class);
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StudentFeedDto> updateStudentPost(@RequestBody StudentFeedDto studentFeedDto) {
        StudentFeed studentPublication = modelMapper.map(studentFeedDto, StudentFeed.class);
        studentPublication.setHashtag(studentFeedDto.getHashtag());
        studentPublication.setMedia(studentFeedDto.getMedia());
        studentPublication.setComments(studentFeedDto.getCommentBox());
        studentFeedService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentFeedDto.class), HttpStatus.OK);
    }
}

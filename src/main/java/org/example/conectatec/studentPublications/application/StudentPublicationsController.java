package org.example.conectatec.studentPublications.application;

import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.example.conectatec.studentPublications.domain.StudentPublicationsService;
import org.example.conectatec.studentPublications.dto.StudentPublicationsDto;
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
public class StudentPublicationsController {

    @Autowired
    private StudentPublicationsService studentPublicationsService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<StudentPublicationsDto> findStudentPostById(@PathVariable Long id) {
        StudentPublications studentPublication = studentPublicationsService.findStudentPublicationById(id);
        StudentPublicationsDto studentPublicationsDto = modelMapper.map(studentPublication, StudentPublicationsDto.class);
        return new ResponseEntity<>(studentPublicationsDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentPublicationsDto>> findAllStudentPosts() {
        List<StudentPublications> studentPosts = studentPublicationsService.findAllPublications();
        Type ListType = new TypeToken<List<StudentPublicationsDto>>() {}.getType();
        List<StudentPublicationsDto> studentPostsDto = modelMapper.map(studentPosts, ListType);
        return new ResponseEntity<>(studentPostsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentPublicationsDto> createStudentPost(@RequestBody StudentPublicationsDto studentPublicationsDto) {
        StudentPublications studentPublication = modelMapper.map(studentPublicationsDto, StudentPublications.class);
        studentPublicationsService.saveStudentPublication(studentPublication);
        return new ResponseEntity<>(modelMapper.map(studentPublication, StudentPublicationsDto.class), HttpStatus.CREATED);
    }

}

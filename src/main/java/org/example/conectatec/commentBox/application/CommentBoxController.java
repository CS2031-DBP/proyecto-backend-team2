package org.example.conectatec.commentBox.application;


import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.commentBox.domain.CommentBoxService;
import org.example.conectatec.commentBox.dto.CommentBoxDto;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentBoxController {

    @Autowired
    private CommentBoxService commentBoxService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<CommentBoxDto> createComment(@RequestBody CommentBox commentBox) {
        CommentBox savedComment = commentBoxService.saveCommentBox(commentBox);
        CommentBoxDto commentBoxDto = modelMapper.map(savedComment, CommentBoxDto.class);
        return new ResponseEntity<>(commentBoxDto, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentBoxService.deleteCommentBox(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<CommentBox>> getAllComments() {
        List<CommentBox> comments = commentBoxService.findAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
//
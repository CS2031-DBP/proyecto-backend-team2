package org.example.conectatec.comment.application;

import jakarta.validation.Valid;
import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.comment.domain.CommentService;
import org.example.conectatec.comment.dto.CommentDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CommentDto commentDto) {
        Comment savedComment = commentService.saveComment(commentDto);
        CommentDto responseDto = new CommentDto();
        responseDto.setId(savedComment.getId());
        responseDto.setContent(savedComment.getContent());
        responseDto.setStudentId(savedComment.getStudent().getId());
        if (savedComment.getStudentPost() != null) {
            responseDto.setStudentPostId(savedComment.getStudentPost().getId());
        }
        if (savedComment.getClubPost() != null) {
            responseDto.setClubPostId(savedComment.getClubPost().getId());
        }
        if (savedComment.getUtecPost() != null) {
            responseDto.setUtecPostId(savedComment.getUtecPost().getId());
        }
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.findCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
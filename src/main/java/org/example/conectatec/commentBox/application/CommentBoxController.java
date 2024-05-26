package org.example.conectatec.commentBox.application;


import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.commentBox.domain.CommentBoxService;
import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentBoxController {

    @Autowired
    private CommentBoxService commentBoxService;

    @PostMapping
    public ResponseEntity<CommentBox> createComment(@RequestBody CommentBox commentBox) {
        CommentBox savedComment = commentBoxService.saveCommentBox(commentBox);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentBox> getCommentById(@PathVariable Long id) {
        Optional<CommentBox> commentBox = commentBoxService.findCommentBoxById(id);
        return commentBox.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/publication/{publicationId}")
    public ResponseEntity<List<CommentBox>> getCommentsByPublication(@PathVariable Long publicationId) {
        StudentPublications publication = new StudentPublications();
        publication.setId(publicationId);  // por  ID
        List<CommentBox> comments = commentBoxService.findCommentsByPublication(publication);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentBoxService.deleteCommentBox(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<CommentBox>> getAllComments() {
        List<CommentBox> comments = commentBoxService.findAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}

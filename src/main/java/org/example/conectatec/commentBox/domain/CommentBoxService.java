package org.example.conectatec.commentBox.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.conectatec.commentBox.infrastructure.*;

import java.util.List;

@Service
public class CommentBoxService {

    @Autowired
    private CommentBoxRepository commentBoxRepository;

    @Transactional
    public CommentBox saveCommentBox(CommentBox commentBox) {
        return commentBoxRepository.save(commentBox);
    }

    @Transactional
    public CommentBox findCommentBoxById(Long id) {
        return commentBoxRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }

    @Transactional
    public List<CommentBox> findCommentsByPublication(StudentFeed publication) {
        return commentBoxRepository.findByPublication(publication);
    }

    @Transactional
    public void deleteCommentBox(Long id) {
        commentBoxRepository.deleteById(id);
    }

    @Transactional
    public List<CommentBox> findAllComments() {
        return commentBoxRepository.findAll();
    }
}

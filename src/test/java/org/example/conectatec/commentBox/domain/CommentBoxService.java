package org.example.conectatec.commentBox.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.commentBox.infrastructure.CommentBoxRepository;
import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentBoxService {

    @Autowired
    private CommentBoxRepository commentBoxRepository;

    @Transactional
    public CommentBox saveCommentBox(CommentBox commentBox) {
        return commentBoxRepository.save(commentBox);
    }

    @Transactional
    public Optional<CommentBox> findCommentBoxById(Long id) {
        return commentBoxRepository.findById(id);
    }

    @Transactional
    public List<CommentBox> findCommentsByPublication(StudentPublications publication) {
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

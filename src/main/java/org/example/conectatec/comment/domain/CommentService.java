package org.example.conectatec.comment.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.clubPost.infrastructure.ClubPostRepository;
import org.example.conectatec.comment.dto.CommentDto;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.example.conectatec.studentPost.infrastructure.StudentPostRepository;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.example.conectatec.utecServicesPost.infrastructure.UtecServicesPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.conectatec.comment.infrastructure.*;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private StudentPostRepository studentPostRepository;

    @Autowired
    private ClubPostRepository clubPostRepository;

    @Autowired
    private UtecServicesPostRepository utecServicesPostRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Comment saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());

        if (commentDto.getStudentPostId() != null) {
            StudentPost studentPost = studentPostRepository.findById(commentDto.getStudentPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("StudentPost not found"));
            comment.setStudentPost(studentPost);
        }

        if (commentDto.getClubPostId() != null) {
            ClubPost clubPost = clubPostRepository.findById(commentDto.getClubPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("ClubPost not found"));
            comment.setClubPost(clubPost);
        }

        if (commentDto.getUtecPostId() != null) {
            UtecServicesPost utecPost = utecServicesPostRepository.findById(commentDto.getUtecPostId())
                    .orElseThrow(() -> new ResourceNotFoundException("UtecServicesPost not found"));
            comment.setUtecPost(utecPost);
        }

        // Ensure the student exists
        Student student = studentRepository.findById(commentDto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        comment.setStudent(student);

        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Transactional
    public List<Comment> findAllComments() {
        return commentRepository.findAll();
    }
}
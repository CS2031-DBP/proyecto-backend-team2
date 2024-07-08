package org.example.conectatec.comment.infrastructure;

import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByStudentPost(StudentPost publication);
}
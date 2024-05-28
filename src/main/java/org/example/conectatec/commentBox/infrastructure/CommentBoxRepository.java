package org.example.conectatec.commentBox.infrastructure;

import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentBoxRepository extends JpaRepository<CommentBox,Long> {

    List<CommentBox> findByPublication(StudentFeed publication);
}



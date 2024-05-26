package org.example.conectatec.CommentBox.infrastructure;

import org.example.conectatec.CommentBox.domain.CommentBox;
import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentBoxRepository extends JpaRepository<CommentBox,Long> {

    List<CommentBox> findByPublication(StudentPublications publication);
}



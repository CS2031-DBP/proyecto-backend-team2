package org.example.conectatec.studentFeed.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.student.domain.Student;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;


@Data

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashtag;

    private String media;

    private String caption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentBox> comments = new ArrayList<>();


}


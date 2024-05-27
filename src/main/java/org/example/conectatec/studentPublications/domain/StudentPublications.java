package org.example.conectatec.studentPublications.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.student.domain.Student;

import java.util.ArrayList;
import java.util.List;


@Data

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentPublications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashtag;

    @Column(nullable = false)
    private String media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentBox> comments = new ArrayList<>();
}


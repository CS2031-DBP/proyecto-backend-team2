package org.example.conectatec.CommentBox.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.domain.StudentPublications;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentBox {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private StudentPublications publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

}

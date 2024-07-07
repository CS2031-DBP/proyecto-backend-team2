package org.example.conectatec.comment.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publication_id")
    private StudentPost studentPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubPost_id")
    private ClubPost clubPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UtecServicesPost_id")
    private UtecServicesPost utecPost;

}

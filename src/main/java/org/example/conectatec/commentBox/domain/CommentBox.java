package org.example.conectatec.commentBox.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentFeed.domain.StudentFeed;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;

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
    private StudentFeed publication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clubPublication_id")
    private ClubFeed clubPublication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UtecServiceFeed_id")
    private UtecServicesFeed utecPublication;



}

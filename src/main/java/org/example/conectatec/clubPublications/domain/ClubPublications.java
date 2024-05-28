package org.example.conectatec.clubPublications.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.student.domain.Student;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubPublications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    private String media;

    @ManyToOne
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "clubPublication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommentBox> comments;
}

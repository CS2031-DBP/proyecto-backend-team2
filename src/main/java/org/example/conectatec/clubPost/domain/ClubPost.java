package org.example.conectatec.clubPost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;
    private String media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", nullable = true)
    private Club club;

    @ManyToOne
    private Career career;

    @OneToMany(mappedBy = "clubPost", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;
}

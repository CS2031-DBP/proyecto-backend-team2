package org.example.conectatec.clubFeed.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.commentBox.domain.CommentBox;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caption;

    // Ajustamos el campo media
    private String media; // Por el momento esto.

    @ManyToOne
    private Career career;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;


    @OneToMany(mappedBy = "clubPublication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommentBox> comments;






}

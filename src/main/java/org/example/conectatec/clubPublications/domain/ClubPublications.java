package org.example.conectatec.clubPublications.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.commentBox.domain.CommentBox;

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

    // Ajustamos el campo media
    private String media; // Assuming media is stored as a URL or simple string path

    @ManyToOne
    private Career career;

    @OneToMany(mappedBy = "clubPublication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CommentBox> comments;

}

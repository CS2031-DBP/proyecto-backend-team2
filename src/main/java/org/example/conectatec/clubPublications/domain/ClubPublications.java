package org.example.conectatec.clubPublications.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.commentBox.domain.CommentBox;

import javax.print.attribute.standard.Media;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClubPublications {

    @Id
    @GeneratedValue
    private Long id;

    private String caption;

    private Media media;

    @ManyToOne
    private Career career;

    @OneToMany(mappedBy = "clubPublication", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<CommentBox> comments;

}

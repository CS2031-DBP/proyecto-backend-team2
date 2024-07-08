package org.example.conectatec.studentPost.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.image.domain.Image;
import org.example.conectatec.student.domain.Student;
import java.util.ArrayList;
import java.util.List;


@Data

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashtag = ""; // Valor por defecto

    private String media = "";   // Opcional: asignar un valor por defecto
    private String caption = ""; // Opcional: asignar un valor por defecto

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    @OneToMany(mappedBy = "studentPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", nullable = true)
    private Image image; // Relación con la entidad Image
}


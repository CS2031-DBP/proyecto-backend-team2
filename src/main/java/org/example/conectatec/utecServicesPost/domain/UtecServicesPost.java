package org.example.conectatec.utecServicesPost.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.comment.domain.Comment;
import org.example.conectatec.utecServices.domain.UtecServices;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtecServicesPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashtag; // Valor por defecto

    private String media = "";   // Opcional: asignar un valor por defecto
    private String caption = ""; // Opcional: asignar un valor por defecto


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicesUTEC_id")
    private UtecServices servicesUTEC;

    @OneToMany(mappedBy = "utecPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}

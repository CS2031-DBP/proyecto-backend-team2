package org.example.conectatec.servicesUTECPublications.domain;

import jakarta.persistence.*;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.servicesUTEC.domain.ServicesUTEC;

import java.util.ArrayList;
import java.util.List;

public class ServicesUTECPublications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hashtag;

    @Column(nullable = false)
    private String media;

    @Column(nullable = false)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicesUTEC_id")
    private ServicesUTEC servicesUTEC;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentBox> comments = new ArrayList<>();
}

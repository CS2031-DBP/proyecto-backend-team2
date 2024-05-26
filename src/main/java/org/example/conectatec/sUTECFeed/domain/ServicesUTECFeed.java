package org.example.conectatec.sUTECFeed.domain;

import jakarta.persistence.*;
import org.example.conectatec.commentBox.domain.CommentBox;
import org.example.conectatec.sUTEC.domain.ServicesUTEC;

import java.util.ArrayList;
import java.util.List;

public class ServicesUTECFeed {
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

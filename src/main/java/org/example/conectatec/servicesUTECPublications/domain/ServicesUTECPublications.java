package org.example.conectatec.servicesUTECPublications.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.example.conectatec.commentBox.domain.CommentBox;

import java.util.ArrayList;
import java.util.List;

public class ServicesUTECPublications {
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentBox> comments = new ArrayList<>();
}

package org.example.conectatec.sUTEC.domain;


import jakarta.persistence.*;

import org.example.conectatec.sUTECFeed.domain.SUTECFeed;
import org.example.conectatec.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SUTEC extends User {

    @OneToMany(mappedBy = "ServicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SUTECFeed> publications = new ArrayList<>();

}

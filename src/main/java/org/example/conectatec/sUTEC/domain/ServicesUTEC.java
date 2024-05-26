package org.example.conectatec.sUTEC.domain;


import jakarta.persistence.*;

import org.example.conectatec.sUTECFeed.domain.ServicesUTECFeed;
import org.example.conectatec.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ServicesUTEC extends User {

    @OneToMany(mappedBy = "ServicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicesUTECFeed> publications = new ArrayList<>();

}

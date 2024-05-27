package org.example.conectatec.UTECServices.domain;


import jakarta.persistence.*;

import org.example.conectatec.UTECServicesFeed.domain.ServicesUTECFeed;
import org.example.conectatec.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UTECServices extends User {

    @OneToMany(mappedBy = "ServicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicesUTECFeed> publications = new ArrayList<>();

}

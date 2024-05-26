package org.example.conectatec.servicesUTEC.domain;


import jakarta.persistence.*;

import org.example.conectatec.servicesUTECPublications.domain.ServicesUTECPublications;
import org.example.conectatec.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ServicesUTEC extends User {

    @OneToMany(mappedBy = "ServicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicesUTECPublications> publications = new ArrayList<>();

}

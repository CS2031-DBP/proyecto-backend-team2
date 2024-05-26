package org.example.conectatec.servicesUTEC.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.example.conectatec.user.domain.User;

@Entity
public class ServicesUTEC extends User {

    @Id
    @GeneratedValue
    private Long id;

}

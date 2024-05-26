package org.example.conectatec.servicesUTEC.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ServicesUTEC {

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name_tec;
}

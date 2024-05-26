package org.example.conectatec.club.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Club {

    @Id
    @GeneratedValue
    private Long id;


    @Column(unique = true)
    private String carrera;

    @ManyToOne(fetch = FetchType.LAZY)
    private Career career;


}

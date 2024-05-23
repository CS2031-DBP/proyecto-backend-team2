package org.example.conectatec.student.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.user.domain.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends User {

    @Column(unique = true)
    private String carrera;

    @ManyToOne(fetch = FetchType.LAZY)
    private Career career;
}

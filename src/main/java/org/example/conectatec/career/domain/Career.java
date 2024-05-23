package org.example.conectatec.career.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.domain.User;

import java.util.List;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Career {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String facultad;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "career", fetch = FetchType.LAZY)
    private List<Student> students;
}
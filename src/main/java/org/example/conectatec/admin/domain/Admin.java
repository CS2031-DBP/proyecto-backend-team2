package org.example.conectatec.admin.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.user.domain.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Admin extends User {

}

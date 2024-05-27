package org.example.conectatec.utecServices.domain;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.utecServicesFeed.domain.UtecServicesFeed;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtecServices extends User {

    @OneToMany(mappedBy = "ServicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UtecServicesFeed> publications = new ArrayList<>();

}

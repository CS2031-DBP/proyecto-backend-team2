package org.example.conectatec.utecServices.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.example.conectatec.user.domain.User;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UtecServices extends User {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "utec_services_feed_id", unique = true, nullable = false)
    private UtecServicesPost utecServicesPost;

    @OneToMany(mappedBy = "servicesUTEC", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UtecServicesPost> utecServicesPosts = new ArrayList<>();

}

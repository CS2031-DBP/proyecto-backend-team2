package org.example.conectatec.club.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.user.domain.User;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Club extends User {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "club_feed_id", unique = true, nullable = false)
    private ClubFeed clubFeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;


}

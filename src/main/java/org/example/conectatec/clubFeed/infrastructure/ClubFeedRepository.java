package org.example.conectatec.clubFeed.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubFeedRepository extends JpaRepository<ClubFeed, Long> {
    ClubFeed findByCareer(Career career);
}

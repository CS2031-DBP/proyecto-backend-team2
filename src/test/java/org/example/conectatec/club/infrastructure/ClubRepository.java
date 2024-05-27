package org.example.conectatec.club.infrastructure;

import org.example.conectatec.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface ClubRepository extends JpaRepository<Club,Long> {
}

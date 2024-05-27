package org.example.conectatec.club.infrastructure;

import org.example.conectatec.club.domain.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface ClubRepository extends JpaRepository<Club,Long> {

    Optional<Club> findByEmail(String email);

}

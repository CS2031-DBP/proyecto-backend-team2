package org.example.conectatec.clubPublications.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPublicationsRepository extends JpaRepository<ClubPublications, Long> {
    ClubPublications findByClubPublicationsId(Long clubPublicationsId);
    ClubPublications findByClubPublicationsName(String clubPublicationsName);
    ClubPublications findAllByClubPublicationsId(Long clubPublicationsId);
    ClubPublications findAllByClubName(String clubName);
    ClubPublications findByCareer(Career career);
}

package org.example.conectatec.clubPublications.infrastructure;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPublicationsRepository extends JpaRepository<ClubPublications, Long> {
    ClubPublications findPostById(Long id);
    ClubPublications findByCareer(Career career);
}

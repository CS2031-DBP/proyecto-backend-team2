package org.example.conectatec.clubPublications.domain;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.infrastructure.ClubPublicationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubPublicationsService {
    @Autowired
    private ClubPublicationsRepository clubPublicationsRepository;

    public ClubPublications findClubPublicationsById(Long id) {
        return clubPublicationsRepository.findAllByClubPublicationsId(id);
    }

    public List<ClubPublications> findAllClubPublications() {
        return clubPublicationsRepository.findAll();
    }

    public ClubPublications findClubPublicationByCareer(Career career) {
        return clubPublicationsRepository.findByCareer(career);
    }

    public ClubPublications createClubPublication(ClubPublications clubPublication) {
        return clubPublicationsRepository.save(clubPublication);
    }

    public void deleteClubPublicationById(Long id) {
        clubPublicationsRepository.deleteById(id);
    }

}

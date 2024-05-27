package org.example.conectatec.clubPublications.domain;

import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.clubPublications.infrastructure.ClubPublicationsRepository;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubPublicationsService {

    @Autowired
    private ClubPublicationsRepository clubPublicationsRepository;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    public ClubPublications findClubPublicationsById(Long id) {
        Optional<ClubPublications> clubPublications = clubPublicationsRepository.findById(id);
        return clubPublications.orElse(null);
    }

    public List<ClubPublications> findAllClubPublications() {
        return clubPublicationsRepository.findAll();
    }

    public ClubPublications findClubPublicationByCareer(Career career) {
        return clubPublicationsRepository.findByCareer(career);
    }

    public ClubPublications createClubPublication(ClubPublications clubPublication) {

        Club club = clubPublication.getClub();
        Long id = club.getId();

        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");


        return clubPublicationsRepository.save(clubPublication);
    }

    public void deleteClubPublicationById(Long id) {

        ClubPublications clubPublication = clubPublicationsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubPublication not found with id " + id));

        Club club = clubPublication.getClub();
        Long clubId = club.getId();


        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User has no permission to delete this resource");
        }

        // Eliminar la publicación del club
        clubPublicationsRepository.deleteById(id);


        clubPublicationsRepository.deleteById(id);
    }
}

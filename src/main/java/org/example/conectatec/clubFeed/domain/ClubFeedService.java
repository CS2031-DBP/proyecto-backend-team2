package org.example.conectatec.clubFeed.domain;

import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.clubFeed.infrastructure.ClubFeedRepository;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubFeedService {

    @Autowired
    private ClubFeedRepository clubFeedRepository;

    @Autowired
    private AuthorizationUtils authorizationUtils;

    public ClubFeed findClubPublicationsById(Long id) {
        Optional<ClubFeed> clubPublications = clubFeedRepository.findById(id);
        return clubPublications.orElse(null);
    }

    public List<ClubFeed> findAllClubPublications() {
        return clubFeedRepository.findAll();
    }

    public ClubFeed findClubPublicationByCareer(Career career) {
        return clubFeedRepository.findByCareer(career);
    }

    public ClubFeed createClubPublication(ClubFeed clubPublication) {

        Club club = clubPublication.getClub();
        Long id = club.getId();

        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");


        return clubFeedRepository.save(clubPublication);
    }

    public void deleteClubPublicationById(Long id) {

        ClubFeed clubPublication = clubFeedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubPublication not found with id " + id));

        Club club = clubPublication.getClub();
        Long clubId = club.getId();


        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User has no permission to delete this resource");
        }

        // Eliminar la publicación del club
        clubFeedRepository.deleteById(id);


        clubFeedRepository.deleteById(id);
    }
}

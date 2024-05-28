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
import org.example.conectatec.clubFeed.dto.ClubFeedDto;

@Service
public class ClubFeedService {


    private final ClubFeedRepository clubFeedRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ClubFeedService(ClubFeedRepository clubFeedRepository, AuthorizationUtils authorizationUtils) {
        this.clubFeedRepository = clubFeedRepository;
        this.authorizationUtils = authorizationUtils;
    }

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
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Club club = clubPublication.getClub();
        Long id = club.getId();

        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");


        return clubFeedRepository.save(clubPublication);
    }

    public void deleteClubPublicationById(Long id) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        ClubFeed clubPublication = clubFeedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubPublication not found with id " + id));

        Club club = clubPublication.getClub();
        Long clubId = club.getId();


        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User has no permission to delete this resource");
        }

        clubFeedRepository.deleteById(id);


        clubFeedRepository.deleteById(id);
    }
    public ClubFeed updateClubPublication(Long id, ClubFeedDto clubFeedDto) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        ClubFeed existingPublication = clubFeedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club publication not found with id: " + id));

        Long clubId = existingPublication.getClub().getId();
        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User does not have permission to update this resource");
        }

        mapDtoToEntity(clubFeedDto, existingPublication);

        return clubFeedRepository.save(existingPublication);
    }

    public ClubFeed partiallyUpdateClubPublication(Long id, ClubFeedDto clubFeedDto) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        ClubFeed existingPublication = clubFeedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club publication not found with id: " + id));

        Long clubId = existingPublication.getClub().getId();
        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User does not have permission to update this resource");
        }

        mapDtoToEntity(clubFeedDto, existingPublication);

        return clubFeedRepository.save(existingPublication);
    }
    private void mapDtoToEntity(ClubFeedDto clubFeedDto, ClubFeed clubFeed) {
        clubFeed.setCaption(clubFeedDto.getCaption());
        clubFeed.setMedia(clubFeedDto.getMedia());
    }
}

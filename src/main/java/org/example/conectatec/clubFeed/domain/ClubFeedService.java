package org.example.conectatec.clubFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.clubFeed.infrastructure.ClubFeedRepository;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
    @Transactional
    public List<ClubFeed> findAllClubPublications() {
        return clubFeedRepository.findAll();
    }

    @Transactional
    public ClubFeed findClubPublicationByCareer(Career career) {
        return clubFeedRepository.findByCareer(career);
    }

    @Transactional
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
    @Transactional
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

    @Transactional
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

    @Transactional
    public ClubFeed partiallyUpdateClubPublication(Long id, ClubFeedDto clubFeedDto) {
        ClubFeed existingPublication = clubFeedRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club publication not found with id: " + id));

        Long clubId = existingPublication.getClub().getId();
        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        mapDtoToEntity(clubFeedDto, existingPublication);

        return clubFeedRepository.save(existingPublication);
    }
    private void mapDtoToEntity(ClubFeedDto clubFeedDto, ClubFeed clubFeed) {
        clubFeed.setCaption(clubFeedDto.getCaption());
        clubFeed.setMedia(clubFeedDto.getMedia());
    }
}

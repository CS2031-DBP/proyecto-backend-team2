package org.example.conectatec.clubPost.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.clubPost.infrastructure.ClubPostRepository;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.example.conectatec.clubPost.dto.ClubPostDto;

@Service
public class ClubPostService {


    private final ClubPostRepository clubPostRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ClubPostService(ClubPostRepository clubPostRepository, AuthorizationUtils authorizationUtils) {
        this.clubPostRepository = clubPostRepository;
        this.authorizationUtils = authorizationUtils;
    }
    @Transactional
    public List<ClubPost> findAllClubPublications() {
        return clubPostRepository.findAll();
    }

    @Transactional
    public ClubPost findClubPublicationByCareer(Career career) {
        return clubPostRepository.findByCareer(career);
    }

    @Transactional
    public ClubPost createClubPublication(ClubPost clubPublication) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Club club = clubPublication.getClub();
        Long id = club.getId();

        if (!authorizationUtils.isAdminOrResourceOwner(id))
            throw new UnauthorizeOperationException("User has no permission to modify this resource");


        return clubPostRepository.save(clubPublication);
    }
    @Transactional
    public void deleteClubPublicationById(Long id) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        ClubPost clubPublication = clubPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ClubPublication not found with id " + id));

        Club club = clubPublication.getClub();
        Long clubId = club.getId();


        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User has no permission to delete this resource");
        }

        clubPostRepository.deleteById(id);


        clubPostRepository.deleteById(id);
    }

    @Transactional
    public ClubPost updateClubPublication(Long id, ClubPostDto clubPostDto) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        ClubPost existingPublication = clubPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club publication not found with id: " + id));

        Long clubId = existingPublication.getClub().getId();
        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("User does not have permission to update this resource");
        }

        mapDtoToEntity(clubPostDto, existingPublication);

        return clubPostRepository.save(existingPublication);
    }

    @Transactional
    public ClubPost partiallyUpdateClubPublication(Long id, ClubPostDto clubPostDto) {
        ClubPost existingPublication = clubPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club publication not found with id: " + id));

        Long clubId = existingPublication.getClub().getId();
        if (!authorizationUtils.isAdminOrResourceOwner(clubId)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        mapDtoToEntity(clubPostDto, existingPublication);

        return clubPostRepository.save(existingPublication);
    }
    private void mapDtoToEntity(ClubPostDto clubPostDto, ClubPost clubPost) {
        clubPost.setCaption(clubPostDto.getCaption());
        clubPost.setMedia(clubPostDto.getMedia());
    }
}

package org.example.conectatec.club.domain;

import jakarta.servlet.http.HttpServletResponse;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private final AuthorizationUtils authorizationUtils;
    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    public ClubService(ClubRepository clubRepository, AuthorizationUtils authorizationUtils) {
        this.clubRepository = clubRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public ClubDto getClubInfo(Long id) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Club club = ClubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        ClubDto response = new ClubDto();
        response.setId(club.getId());
        response.setName(club.getName());
        response.setEmail(club.getEmail());
        response.setCarrera(club.getCarrera());;
        ClubFeed clubFeed = Club.getClubFeed();
        ClubFeedDto clubFeedDto = new ClubFeedDto();
        clubFeedDto.setId(clubFeed.getId());
        clubFeed.
        return ;
    }

    public List<Club> getAllClubs() {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return clubRepository.findAll();
    }

    public Club createClub(Club club) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return clubRepository.save(club);
    }

    public void deleteClubById(Long id) {
        if(!authorizationUtils.isAdminOrResourceOwner(id)){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Club club = clubRepository.findById(id).get();
        clubRepository.delete(club);
    }

}

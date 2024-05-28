package org.example.conectatec.club.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.club.dto.ClubUpdateDto;
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
    private final ClubRepository clubRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ClubService(ClubRepository clubRepository, AuthorizationUtils authorizationUtils) {
        this.clubRepository = clubRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public ClubDto getClubInfo(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Club not found"));
        return mapToDto(club);
    }

    public List<Club> getAllClubs() {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return clubRepository.findAll();
    }

    public Club createClub(Club club) {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return clubRepository.save(club);
    }

    public void deleteClubById(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Club not found"));
        clubRepository.delete(club);
    }

    @Transactional
    public ClubDto updateClub(Long id, ClubUpdateDto clubUpdateDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        club.setName(clubUpdateDto.getName());
        club.setEmail(clubUpdateDto.getEmail());
        club.setCarrera(clubUpdateDto.getCarrera());
        club.setPassword(clubUpdateDto.getPassword());

        clubRepository.save(club);
        return mapToDto(club);
    }

    @Transactional
    public ClubDto partiallyUpdateClub(Long id, ClubUpdateDto clubUpdateDto) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        if (clubUpdateDto.getName() != null) {
            club.setName(clubUpdateDto.getName());
        }
        if (clubUpdateDto.getEmail() != null) {
            club.setEmail(clubUpdateDto.getEmail());
        }
        if (clubUpdateDto.getCarrera() != null) {
            club.setCarrera(clubUpdateDto.getCarrera());
        }

        clubRepository.save(club);
        return mapToDto(club);
    }

    private ClubDto mapToDto(Club club) {
        ClubDto response = new ClubDto();
        response.setId(club.getId());
        response.setName(club.getName());
        response.setEmail(club.getEmail());
        response.setCarrera(club.getCarrera());

        ClubFeed clubFeed = club.getClubFeed();
        if (clubFeed != null) {
            ClubFeedDto clubFeedDto = new ClubFeedDto();
            clubFeedDto.setId(clubFeed.getId());
            clubFeedDto.setCaption(clubFeed.getCaption());
            clubFeedDto.setMedia(clubFeed.getMedia());
            response.setClubFeed(clubFeedDto);
        }

        return response;
    }
}

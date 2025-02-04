package org.example.conectatec.club.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.dto.CareerDto;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.club.dto.ClubUpdateDto;
import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.clubPost.dto.ClubPostDto;
import org.example.conectatec.comment.dto.CommentDto;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public ClubService(ClubRepository clubRepository, AuthorizationUtils authorizationUtils) {
        this.clubRepository = clubRepository;
        this.authorizationUtils = authorizationUtils;
    }
    @Transactional
    public ClubDto getClubInfo(Long id) {

        Club club = clubRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Club not found"));
        return mapToDto(club);
    }
    @Transactional
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }
    @Transactional
    public Club createClub(Club club) {
        String username = authorizationUtils.getCurrentUserEmail();
        if (username == null) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return clubRepository.save(club);
    }
    @Transactional
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


        clubRepository.save(club);
        return mapToDto(club);
    }

    private ClubDto mapToDto(Club club) {
        ClubDto response = new ClubDto();
        response.setId(club.getId());
        response.setName(club.getName());
        response.setEmail(club.getEmail());

        Career career = club.getCareer();
        if (career != null) {
            CareerDto careerDto = new CareerDto();
            careerDto.setId(career.getId());
            careerDto.setName(career.getName());
            careerDto.setFacultad(career.getFacultad());
            response.setCareer(careerDto);
        }

        List<ClubPost> clubPosts = club.getPosts();
        if (clubPosts != null && !clubPosts.isEmpty()) {
            List<ClubPostDto> clubPostDtos = clubPosts.stream().map(post -> {
                ClubPostDto clubPostDto = new ClubPostDto();
                clubPostDto.setId(post.getId());
                clubPostDto.setCaption(post.getCaption());
                clubPostDto.setMedia(post.getMedia());

                CareerDto postCareerDto = new CareerDto();
                postCareerDto.setId(post.getCareer().getId());
                postCareerDto.setName(post.getCareer().getName());
                postCareerDto.setFacultad(post.getCareer().getFacultad());
                clubPostDto.setCareer(postCareerDto);

                List<CommentDto> commentDtos = post.getComments().stream().map(comment -> {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(comment.getId());
                    commentDto.setContent(comment.getContent());
                    return commentDto;
                }).collect(Collectors.toList());
                clubPostDto.setComments(commentDtos);

                return clubPostDto;
            }).collect(Collectors.toList());

            response.setClubPosts(clubPostDtos);
        }

        return response;
    }
}

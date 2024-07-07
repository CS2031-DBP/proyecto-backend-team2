package org.example.conectatec.clubPost.application;

import jakarta.validation.Valid;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.clubPost.domain.ClubPostService;
import org.example.conectatec.clubPost.dto.ClubPostDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/club-publications")
public class ClubPostController {

    private final ClubPostService clubPostService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClubPostController(ClubPostService clubFeedService, ModelMapper modelMapper) {
        this.clubPostService = clubFeedService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<ClubPostDto>> getAllClubPublications() {
        List<ClubPost> clubs = clubPostService.findAllClubPublications();
        Type listType = new TypeToken<List<ClubDto>>() {}.getType();
        List<ClubPostDto> clubFeedDtos = modelMapper.map(clubs, listType);
        return new ResponseEntity<>(clubFeedDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/career")
    public ResponseEntity<ClubPostDto> getClubPublicationByCareer(@RequestParam Career career) {
        ClubPost publicationByCareer = clubPostService.findClubPublicationByCareer(career);
        ClubPostDto clubFeedDto = modelMapper.map(publicationByCareer, ClubPostDto.class);
        return new ResponseEntity<>(clubFeedDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB')")
    @PostMapping
    public ResponseEntity<ClubPostDto> createClubPublications(@RequestBody ClubPost clubPublication) {
        ClubPost publication = clubPostService.createClubPublication(clubPublication);
        ClubPostDto clubFeedDto = modelMapper.map(publication, ClubPostDto.class);
        return new ResponseEntity<>(clubFeedDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('CLUB')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClubPublications(@PathVariable Long id) {
        clubPostService.deleteClubPublicationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('CLUB')")
    @PutMapping("/{id}")
    public ResponseEntity<ClubPostDto> updateClubPublication(@PathVariable Long id, @Valid @RequestBody ClubPostDto clubPostDto) {
        ClubPost updatedPublication = clubPostService.updateClubPublication(id, clubPostDto);
        ClubPostDto updatedPublicationDto = modelMapper.map(updatedPublication, ClubPostDto.class);
        return new ResponseEntity<>(updatedPublicationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLUB')")
    @PatchMapping("/{id}")
    public ResponseEntity<ClubPostDto> partiallyUpdateClubPublication(@PathVariable Long id, @Valid @RequestBody ClubPostDto clubFeedDto) {
        ClubPost updatedPublication = clubPostService.partiallyUpdateClubPublication(id, clubFeedDto);
        ClubPostDto updatedPublicationDto = modelMapper.map(updatedPublication, ClubPostDto.class);
        return new ResponseEntity<>(updatedPublicationDto, HttpStatus.OK);
    }
}

package org.example.conectatec.clubFeed.application;

import jakarta.validation.Valid;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.clubFeed.domain.ClubFeed;
import org.example.conectatec.clubFeed.domain.ClubFeedService;
import org.example.conectatec.clubFeed.dto.ClubFeedDto;
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
public class ClubFeedController {

    private final ClubFeedService clubFeedService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClubFeedController(ClubFeedService clubFeedService, ModelMapper modelMapper) {
        this.clubFeedService = clubFeedService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<ClubFeedDto>> getAllClubPublications() {
        List<ClubFeed> clubs = clubFeedService.findAllClubPublications();
        Type listType = new TypeToken<List<ClubDto>>() {}.getType();
        List<ClubFeedDto> clubFeedDtos = modelMapper.map(clubs, listType);
        return new ResponseEntity<>(clubFeedDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/career")
    public ResponseEntity<ClubFeedDto> getClubPublicationByCareer(@RequestParam Career career) {
        ClubFeed publicationByCareer = clubFeedService.findClubPublicationByCareer(career);
        ClubFeedDto clubFeedDto = modelMapper.map(publicationByCareer, ClubFeedDto.class);
        return new ResponseEntity<>(clubFeedDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB')")
    @PostMapping
    public ResponseEntity<ClubFeedDto> createClubPublications(@RequestBody ClubFeed clubPublication) {
        ClubFeed publication = clubFeedService.createClubPublication(clubPublication);
        ClubFeedDto clubFeedDto = modelMapper.map(publication, ClubFeedDto.class);
        return new ResponseEntity<>(clubFeedDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('CLUB')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClubPublications(@PathVariable Long id) {
        clubFeedService.deleteClubPublicationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PreAuthorize("hasRole('CLUB')")
    @PutMapping("/{id}")
    public ResponseEntity<ClubFeedDto> updateClubPublication(@PathVariable Long id, @Valid @RequestBody ClubFeedDto clubFeedDto) {
        ClubFeed updatedPublication = clubFeedService.updateClubPublication(id, clubFeedDto);
        ClubFeedDto updatedPublicationDto = modelMapper.map(updatedPublication, ClubFeedDto.class);
        return new ResponseEntity<>(updatedPublicationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLUB')")
    @PatchMapping("/{id}")
    public ResponseEntity<ClubFeedDto> partiallyUpdateClubPublication(@PathVariable Long id, @Valid @RequestBody ClubFeedDto clubFeedDto) {
        ClubFeed updatedPublication = clubFeedService.partiallyUpdateClubPublication(id, clubFeedDto);
        ClubFeedDto updatedPublicationDto = modelMapper.map(updatedPublication, ClubFeedDto.class);
        return new ResponseEntity<>(updatedPublicationDto, HttpStatus.OK);
    }
}

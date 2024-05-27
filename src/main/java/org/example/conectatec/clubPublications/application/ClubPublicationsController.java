package org.example.conectatec.clubPublications.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.example.conectatec.clubPublications.domain.ClubPublicationsService;
import org.example.conectatec.clubPublications.dto.ClubPublicationsDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping("/club-publications")
public class ClubPublicationsController {

    @Autowired
    private ClubPublicationsService clubPublicationsService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ClubPublicationsDto> getClubPublication(@PathVariable Long id) {
        ClubPublications publication = clubPublicationsService.findClubPublicationsById(id);
        ClubPublicationsDto clubPublicationsDto = modelMapper.map(publication, ClubPublicationsDto.class);
        return new ResponseEntity<>(clubPublicationsDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClubPublicationsDto>> getClubPublications() {
        List<ClubPublications> clubs = clubPublicationsService.findAllClubPublications();
        Type listType = new TypeToken<List<ClubDto>>() {}.getType();
        List<ClubPublicationsDto> clubPublicationsDtos = modelMapper.map(clubs, listType);
        return new ResponseEntity<>(clubPublicationsDtos, HttpStatus.OK);
    }

    @GetMapping("/career")
    public ResponseEntity<ClubPublicationsDto> getClubPublicationByCareer(@RequestParam Career career) {
        ClubPublications publicationByCareer = clubPublicationsService.findClubPublicationByCareer(career);
        ClubPublicationsDto clubPublicationsDto = modelMapper.map(publicationByCareer, ClubPublicationsDto.class);
        return new ResponseEntity<>(clubPublicationsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClubPublicationsDto> createClubPublications(@RequestBody ClubPublications clubPublication) {
        ClubPublications publication = clubPublicationsService.createClubPublication(clubPublication);
        ClubPublicationsDto clubPublicationsDto = modelMapper.map(publication, ClubPublicationsDto.class);
        return new ResponseEntity<>(clubPublicationsDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClubPublications(@PathVariable Long id) {
        clubPublicationsService.deleteClubPublicationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

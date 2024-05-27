package org.example.conectatec.clubPublications.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.example.conectatec.clubPublications.domain.ClubPublicationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club-publications")
public class ClubPublicationsController {

    @Autowired
    private ClubPublicationsService clubPublicationsService;

    @GetMapping("/{id}")
    public ResponseEntity<ClubPublications> getClubPublication(@PathVariable Long id) {
        ClubPublications publication = clubPublicationsService.findClubPublicationById(id);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClubPublications>> getClubPublications() {
        List<ClubPublications> posts = clubPublicationsService.findAllClubPublications();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/career")
    public ResponseEntity<ClubPublications> getClubPublicationByCareerId(@RequestParam Career career) {
        ClubPublications publicationByCareer = clubPublicationsService.findClubPublicationByCareer(career);
        return new ResponseEntity<>(publicationByCareer, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClubPublications> createClubPublications(@RequestBody ClubPublications clubPublication) {
        ClubPublications publication = clubPublicationsService.createClubPublication(clubPublication);
        return new ResponseEntity<>(publication, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClubPublications(@RequestBody Long id) {
        clubPublicationsService.deleteClubPublicationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package org.example.conectatec.clubPublications.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.clubPublications.domain.ClubPublications;
import org.example.conectatec.clubPublications.domain.ClubPublicationsService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(publication);
    }

    @GetMapping
    public ResponseEntity<List<ClubPublications>> getClubPublications() {
        clubPublicationsService.findAllClubPublications();
        return ResponseEntity.ok(clubPublicationsService.findAllClubPublications());
    }

    @GetMapping("/career")
    public ResponseEntity<ClubPublications> getClubPublicationByCareerId(@RequestParam Career career) {
        ClubPublications publicationByCareer = clubPublicationsService.findClubPublicationByCareer(career);
        return ResponseEntity.ok(publicationByCareer);
    }

    @PostMapping
    public ResponseEntity<ClubPublications> createClubPublications(@RequestBody ClubPublications clubPublication) {
        ClubPublications publication = clubPublicationsService.createClubPublication(clubPublication);
        return ResponseEntity.ok(publication);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteClubPublications(@RequestBody Long id) {
        clubPublicationsService.deleteClubPublicationById(id);
        return ResponseEntity.noContent().build();
    }
}

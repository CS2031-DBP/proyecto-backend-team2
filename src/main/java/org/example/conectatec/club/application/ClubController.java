package org.example.conectatec.club.application;

import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.domain.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @GetMapping("/{id}")
    public ResponseEntity<Club> getClub(@RequestBody Long id) {
        Club club = clubService.getClubById(id);
        return ResponseEntity.ok(club);
    }

    @GetMapping
    public ResponseEntity<List<Club>> getClubs() {
        List<Club> clubs = clubService.getAllClubs();
        return ResponseEntity.ok(clubs);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Club> createClub(@RequestBody Club club) {
        Club clubCreated = clubService.createClub(club);
        return ResponseEntity.ok(clubCreated);
    }

    @DeleteMapping
    public ResponseEntity<Club> deleteClub(@RequestBody Long id) {
        clubService.deleteClubById(id);
        return ResponseEntity.noContent().build();
    }
}

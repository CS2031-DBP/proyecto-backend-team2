package org.example.conectatec.club.application;

import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.domain.ClubService;
import org.example.conectatec.club.dto.ClubDto;
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
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ClubDto> getClub(@PathVariable Long id) {
        Club club = clubService.getClubInfo(id);
        ClubDto clubDto = modelMapper.map(club, ClubDto.class);
        return new ResponseEntity<>(clubDto, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping
    public ResponseEntity<List<ClubDto>> getClubs() {
        List<Club> clubs = clubService.getAllClubs();
        Type listType = new TypeToken<List<ClubDto>>() {}.getType();
        List<ClubDto> clubDtos = modelMapper.map(clubs, listType);
        return new ResponseEntity<>(clubDtos, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('CLUB')")
    @PostMapping("/{id}")
    public ResponseEntity<ClubDto> createClub(@RequestBody Club club) {
        Club clubCreated = clubService.createClub(club);
        ClubDto clubDto = modelMapper.map(clubCreated, ClubDto.class);
        return new ResponseEntity<>(clubDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('CLUB')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClub(@PathVariable Long id) {
        clubService.getClubInfo(id);
        clubService.deleteClubById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package org.example.conectatec.club.application;

import jakarta.validation.Valid;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.domain.ClubService;
import org.example.conectatec.club.dto.ClubDto;
import org.example.conectatec.club.dto.ClubUpdateDto;
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

    private final ClubService clubService;
    private final ModelMapper modelMapper;

    @Autowired
    public ClubController(ClubService clubService, ModelMapper modelMapper) {
        this.clubService = clubService;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("hasRole('CLUB') or hasRole('UTEC') or hasRole('STUDENT')")
    @GetMapping("/clubs/{id}")
    public ResponseEntity<ClubDto> getClub(@PathVariable Long id) {
        ClubDto club = clubService.getClubInfo(id);
        return new ResponseEntity<>(club, HttpStatus.OK);
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
    @PreAuthorize("hasRole('CLUB')")
    @PutMapping("/{id}")
    public ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @Valid @RequestBody ClubUpdateDto clubUpdateDto) {
        ClubDto updatedClub = clubService.updateClub(id, clubUpdateDto);
        return new ResponseEntity<>(updatedClub, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLUB') ")
    @PatchMapping("/{id}")
    public ResponseEntity<ClubDto> partiallyUpdateClub(@PathVariable Long id, @Valid @RequestBody ClubUpdateDto clubUpdateDto) {
        ClubDto updatedClub = clubService.partiallyUpdateClub(id, clubUpdateDto);
        return new ResponseEntity<>(updatedClub, HttpStatus.OK);
    }
}

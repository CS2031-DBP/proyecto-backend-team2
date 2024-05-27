package org.example.conectatec.club.domain;

import org.example.conectatec.club.infrastructure.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    @Autowired
    private ClubRepository clubRepository;

    public Club getClubById(Long id) {
        return clubRepository.findById(id).get();
    }

    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    public Club createClub(Club club) {



        return clubRepository.save(club);
    }

    public void deleteClubById(Long id) {
        Club club = clubRepository.findById(id).get();
        clubRepository.delete(club);
    }

}

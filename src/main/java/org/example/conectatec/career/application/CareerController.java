package org.example.conectatec.career.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.domain.CareerService;
import org.example.conectatec.career.dto.CareerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career")
public class CareerController {

    @Autowired
    private CareerService careerService;
    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasRole('STUDENT') or hasRole('CLUB')")
    @GetMapping("/{id}")
    public ResponseEntity<CareerDto> getCareer(@PathVariable Long id) {
        CareerDto careerDto = modelMapper.map(careerService.getCareerById(id), CareerDto.class);
        return ResponseEntity.ok(careerDto);
    }
    @PreAuthorize("hasRole('CLUB')")
    @GetMapping
    public ResponseEntity<List<Career>> getClubs() {
        List<Career> careers = careerService.getAllCareers();
        return ResponseEntity.ok(careers);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public ResponseEntity<CareerDto> createCareer(@RequestBody Career career) {
        Career careerCreated = careerService.createCareer(career);
        CareerDto careerDto = modelMapper.map(careerCreated, CareerDto.class);
        return new ResponseEntity<>(careerDto, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<Career> deleteCareer(@RequestBody Long id) {
        careerService.deleteCareerById(id);
        return ResponseEntity.noContent().build();
    }
}
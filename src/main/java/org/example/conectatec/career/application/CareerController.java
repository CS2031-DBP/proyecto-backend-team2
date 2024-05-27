package org.example.conectatec.career.application;

import org.example.conectatec.career.domain.Career;
import org.example.conectatec.career.domain.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/career")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping("/{id}")
    public ResponseEntity<Career> getCareer(@RequestBody Long id) {
        Career career = careerService.getCareerById(id);
        return ResponseEntity.ok(career);
    }

    @GetMapping
    public ResponseEntity<List<Career>> getClubs() {
        List<Career> careers = careerService.getAllCareers();
        return ResponseEntity.ok(careers);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Career> createCareer(@RequestBody Career career) {
        Career careerCreated = careerService.createCareer(career);
        return ResponseEntity.ok(careerCreated);
    }

    @DeleteMapping
    public ResponseEntity<Career> deleteCareer(@RequestBody Long id) {
        careerService.deleteCareerById(id);
        return ResponseEntity.noContent().build();
    }
}
package org.example.conectatec.career.domain;


import org.example.conectatec.career.infrastructure.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerService {


    @Autowired
    private CareerRepository careerRepository;

    public Career saveCareer(Career career) {
        return careerRepository.save(career);
    }

    public Optional<Career> findCareerById(Long id) {
        return careerRepository.findById(id);
    }

    public Optional<Career> findCareerByName(String name) {
        return careerRepository.findByName(name);
    }

    public void deleteCareer(Long id) {
        careerRepository.deleteById(id);
    }

    public List<Career> findAllCareers() {
        return careerRepository.findAll();
    }

}

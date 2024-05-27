package org.example.conectatec.career.domain;


import org.example.conectatec.career.infrastructure.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public Career getCareerById(Long id) {
        return careerRepository.findById(id).get();
    }

    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    public Career createCareer(Career career) {
        return careerRepository.save(career);
    }

    public void deleteCareerById(Long id) {
        Career career = careerRepository.findById(id).get();
        careerRepository.delete(career);
    }

}

package org.example.conectatec.career.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Transactional
    public Career getCareerById(Long id) {
        return careerRepository.findById(id).get();
    }

    @Transactional
    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    @Transactional
    public Career createCareer(Career career) {
        return careerRepository.save(career);
    }

    @Transactional
    public void deleteCareerById(Long id) {
        Career career = careerRepository.findById(id).get();
        careerRepository.delete(career);
    }

}

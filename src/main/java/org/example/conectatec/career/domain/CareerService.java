package org.example.conectatec.career.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.utils.AuthorizationUtils;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    private final AuthorizationUtils authorizationUtils;
    @Autowired
    public CareerService(CareerRepository careerRepository, AuthorizationUtils authorizationUtils) {
        this.careerRepository = careerRepository;
        this.authorizationUtils = authorizationUtils;
    }

    @Transactional
    public Career getCareerById(Long id) {
        return careerRepository.findById(id).orElseThrow();
    }

    @Transactional
    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    @Transactional
    public Career createCareer(Career career) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return careerRepository.save(career);
    }

    @Transactional
    public void deleteCareerById(Long id) {

        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }

        Career career = careerRepository.findById(id).get();
        careerRepository.delete(career);
    }

}

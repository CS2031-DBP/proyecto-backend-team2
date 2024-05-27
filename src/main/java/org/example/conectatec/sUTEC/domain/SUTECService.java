package org.example.conectatec.sUTEC.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.sUTEC.infrastructure.SUTECRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class SUTECService {

    @Autowired
    private SUTECRepository sutecRepository;


    @Transactional
    public SUTEC saveUtecServices(SUTEC sutec) {
        return sutecRepository.save(sutec);
    }

    @Transactional
    public Optional<SUTEC> findUtecServicesById(Long id) {
        return sutecRepository.findById(id);
    }

    @Transactional
    public Optional<SUTEC> findUtecServicesByEmail(String email) {
        return sutecRepository.findByEmail(email);
    }

    @Transactional
    public void deleteUtecServices_porId(Long id) {
        sutecRepository.deleteById(id);
    }

    @Transactional
    public List<SUTEC> findAllUtecServices() {
        return sutecRepository.findAll();
    }

}

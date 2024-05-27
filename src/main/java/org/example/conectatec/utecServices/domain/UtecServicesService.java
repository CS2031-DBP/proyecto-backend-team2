package org.example.conectatec.utecServices.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UtecServicesService {

    @Autowired
    private UtecServicesRepository utecServicesRepository;


    @Transactional
    public UtecServices saveUtecServices(UtecServices sutec) {
        return utecServicesRepository.save(sutec);
    }

    @Transactional
    public Optional<UtecServices> findUtecServicesById(Long id) {
        return utecServicesRepository.findById(id);
    }

    @Transactional
    public Optional<UtecServices> findUtecServicesByEmail(String email) {
        return utecServicesRepository.findByEmail(email);
    }

    @Transactional
    public void deleteUtecServices_porId(Long id) {
        utecServicesRepository.deleteById(id);
    }

    @Transactional
    public List<UtecServices> findAllUtecServices() {
        return utecServicesRepository.findAll();
    }

}

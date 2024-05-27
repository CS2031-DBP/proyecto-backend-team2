package org.example.conectatec.sUTECFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.sUTEC.infrastructure.SUTECRepository;

import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.domain.StudentPublications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class SUTECFeedService {

    @Autowired
    private SUTECRepository repository;

    @Transactional
    public SUTECFeed saveServicesUTECPublications(SUTECFeed servicesUTECPublications) {
        return servicesutecpublicationsrepository.save(servicesUTECPublications);
    }

    @Transactional
    public Optional<SUTECFeed> findservicesutecpublicationsrepositoryById(Long id) {
        return servicesutecpublicationsrepository.findById(id);
    }

    @Transactional
    public List<StudentPublications> findPublicationsByStudent(Student student) {
        return studentPublicationsRepository.findByStudent(student);
    }

    @Transactional
    public void deleteStudentPublication(Long id) {
        studentPublicationsRepository.deleteById(id);
    }

    @Transactional
    public List<StudentPublications> findAllPublications() {
        return studentPublicationsRepository.findAll();
    }

}

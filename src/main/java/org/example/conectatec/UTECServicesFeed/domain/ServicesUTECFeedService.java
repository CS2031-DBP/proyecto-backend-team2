package org.example.conectatec.UTECServicesFeed.domain;

import jakarta.transaction.Transactional;

import org.example.conectatec.student.domain.Student;
import org.example.conectatec.studentPublications.domain.StudentPublications;

import java.util.List;
import java.util.Optional;

public class ServicesUTECFeedService {



    @Transactional
    public ServicesUTECFeed saveServicesUTECPublications(ServicesUTECFeed servicesUTECPublications) {
        return servicesutecpublicationsrepository.save(servicesUTECPublications);
    }

    @Transactional
    public Optional<ServicesUTECFeed> findservicesutecpublicationsrepositoryById(Long id) {
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

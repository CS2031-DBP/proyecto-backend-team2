package org.example.conectatec.studentFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.studentFeed.infrastructure.StudentFeedRepository;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.example.conectatec.auth.utils.AuthorizationUtils;

@Service
public class StudentFeedService {

    private final StudentFeedRepository studentFeedRepository;
    private final AuthorizationUtils authorizationUtils;
    @Autowired
    public StudentFeedService(StudentFeedRepository studentFeedRepository, AuthorizationUtils authorizationUtils) {
        this.studentFeedRepository = studentFeedRepository;
        this.authorizationUtils = authorizationUtils;
    }

    @Transactional
    public StudentFeed saveStudentPublication(StudentFeed studentPublication) {
        return studentFeedRepository.save(studentPublication);
    }

    @Transactional
    public void deleteStudentPublication(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        studentFeedRepository.deleteById(id);
    }

    @Transactional
    public List<StudentFeed> findAllPublications() {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return studentFeedRepository.findAll();
    }
    @Transactional
    public StudentFeed findStudentPublicationByCareer(Career career) {
        Optional<StudentFeed> studentFeed = studentFeedRepository.findByCareer(career);
        return studentFeed.orElseThrow(()-> new ResourceNotFoundException("Student post not found"));
    }

    @Transactional
    public StudentFeed findById(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        return studentFeedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
    }
}


package org.example.conectatec.studentFeed.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.studentFeed.infrastructure.StudentFeedRepository;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.example.conectatec.auth.utils.AuthorizationUtils;

@Service
public class StudentFeedService {

    private final StudentFeedRepository studentFeedRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    @Autowired
    public StudentFeedService(StudentFeedRepository studentFeedRepository, AuthorizationUtils authorizationUtils, ModelMapper modelMapper) {
        this.studentFeedRepository = studentFeedRepository;
        this.authorizationUtils = authorizationUtils;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveStudentPublication(StudentFeed studentPublication) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        studentFeedRepository.save(studentPublication);
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
        return studentFeedRepository.findAll();
    }
    @Transactional
    public StudentFeed findStudentPublicationByCareer(Career career) {
        return studentFeedRepository.findByCareer(career);
    }

    @Transactional
    public StudentFeed updateStudentPublication(Long id, StudentFeed updatedStudentFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        StudentFeed existingFeed = studentFeedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StudentFeed not found"));

        modelMapper.map(updatedStudentFeed, existingFeed);
        return studentFeedRepository.save(existingFeed);
    }

    @Transactional
    public StudentFeed partialUpdateStudentPublication(Long id, StudentFeed partialStudentFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        StudentFeed existingFeed = studentFeedRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StudentFeed not found"));

        if (partialStudentFeed.getCaption() != null) {
            existingFeed.setCaption(partialStudentFeed.getCaption());
        }
        if (partialStudentFeed.getMedia() != null) {
            existingFeed.setMedia(partialStudentFeed.getMedia());
        }
        if (partialStudentFeed.getHashtag() != null) {
            existingFeed.setHashtag(partialStudentFeed.getHashtag());
        }

        return studentFeedRepository.save(existingFeed);
    }
}


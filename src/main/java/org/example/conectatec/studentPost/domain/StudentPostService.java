package org.example.conectatec.studentPost.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.career.domain.Career;
import org.example.conectatec.exceptions.ResourceNotFoundException;
import org.example.conectatec.studentPost.infrastructure.StudentPostRepository;
import org.example.conectatec.user.exceptions.UnauthorizeOperationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.example.conectatec.auth.utils.AuthorizationUtils;

@Service
public class StudentPostService {

    private final StudentPostRepository studentPostRepository;
    private final AuthorizationUtils authorizationUtils;
    private final ModelMapper modelMapper;
    @Autowired
    public StudentPostService(StudentPostRepository studentFeedRepository, AuthorizationUtils authorizationUtils, ModelMapper modelMapper) {
        this.studentPostRepository = studentFeedRepository;
        this.authorizationUtils = authorizationUtils;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public void saveStudentPublication(StudentPost studentPublication) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null){
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        studentPostRepository.save(studentPublication);
    }

    @Transactional
    public void deleteStudentPublication(Long id) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        studentPostRepository.deleteById(id);
    }

    @Transactional
    public List<StudentPost> findAllPublications() {
        return studentPostRepository.findAll();
    }
    @Transactional
    public StudentPost findStudentPublicationByCareer(Career career) {
        Optional<StudentPost> studentFeed = studentPostRepository.findByCareer(career);
        return studentFeed.orElseThrow(()-> new ResourceNotFoundException("Student post not found"));
    }

    @Transactional
    public StudentPost updateStudentPublication(Long id, StudentPost updatedStudentFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        StudentPost existingFeed = studentPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StudentFeed not found"));

        modelMapper.map(updatedStudentFeed, existingFeed);
        return studentPostRepository.save(existingFeed);
    }

    @Transactional
    public StudentPost partialUpdateStudentPublication(Long id, StudentPost partialStudentFeed) {
        if (!authorizationUtils.isAdminOrResourceOwner(id)) {
            throw new UnauthorizeOperationException("You do not have permission to access this resource");
        }
        StudentPost existingFeed = studentPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("StudentFeed not found"));

        if (partialStudentFeed.getCaption() != null) {
            existingFeed.setCaption(partialStudentFeed.getCaption());
        }
        if (partialStudentFeed.getMedia() != null) {
            existingFeed.setMedia(partialStudentFeed.getMedia());
        }
        if (partialStudentFeed.getHashtag() != null) {
            existingFeed.setHashtag(partialStudentFeed.getHashtag());
        }

        return studentPostRepository.save(existingFeed);
    }

    @Transactional
    public Long findStudentFeedIdByHashtag(String hashtag) {
        StudentPost studentFeed = studentPostRepository.findByHashtag(hashtag)
                .orElseThrow(() -> new ResourceNotFoundException("StudentFeed not found"));
        return studentFeed.getId();
    }
}

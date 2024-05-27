package org.example.conectatec.user.domain;

import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserBaseRepository<User> userRepository;


    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UtecServicesRepository utecServicesRepository;

    public User findByEmail(String email, String role) {
        User user;
        switch (role) {
            case "ROLE_STUDENT":
                user = studentRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                break;
            case "ROLE_CLUB":
                user = clubRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                break;
            case "ROLE_UTEC":
                user = utecServicesRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                break;
            default:
                throw new UsernameNotFoundException("User not found");
        }
        return user;

    }



    @Bean(name = "UserDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository
                    .findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return (UserDetails) user;
        };
    }


}

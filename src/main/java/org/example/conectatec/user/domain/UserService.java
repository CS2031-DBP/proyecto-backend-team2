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


    private final UserBaseRepository<User> userRepository;
    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;
    private final UtecServicesRepository utecServicesRepository;
    @Autowired
    public UserService(UserBaseRepository<User> userRepository, StudentRepository studentRepository, ClubRepository clubRepository, UtecServicesRepository utecServicesRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.clubRepository = clubRepository;
        this.utecServicesRepository = utecServicesRepository;
    }


    public User findByEmail(String email, String role) {
        User user;
        switch (role) {
            case "STUDENT":
                user = studentRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                break;
            case "CLUB":
                user = clubRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                break;
            case "UTEC":
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

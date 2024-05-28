package org.example.conectatec.user.domain;

import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUtecServices(UtecServices user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return utecServicesRepository.save(user);
    }

    public User registerStudent(Student user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return studentRepository.save(user);
    }

    public User registerClub(Club user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return clubRepository.save(user);
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

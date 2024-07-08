package org.example.conectatec.auth.domain;

import jakarta.transaction.Transactional;
import org.example.conectatec.auth.dto.*;
import org.example.conectatec.auth.exceptions.UserAlreadyExistException;
import org.example.conectatec.career.infrastructure.CareerRepository;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.club.infrastructure.ClubRepository;
import org.example.conectatec.clubPost.domain.ClubPost;
import org.example.conectatec.config.JwtService;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.student.infrastructure.StudentRepository;
import org.example.conectatec.studentPost.domain.StudentPost;
import org.example.conectatec.user.domain.Role;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.example.conectatec.utecServices.infrastructure.UtecServicesRepository;
import org.example.conectatec.utecServicesPost.domain.UtecServicesPost;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserBaseRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final StudentRepository studentRepository;
    private final ClubRepository clubRepository;
    private final UtecServicesRepository utecServicesRepository;
    private final CareerRepository careerRepository;

    @Autowired
    public AuthService(UserBaseRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, ModelMapper modelMapper, StudentRepository studentRepository, ClubRepository clubRepository, UtecServicesRepository utecServicesRepository, CareerRepository careerRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
        this.clubRepository = clubRepository;
        this.utecServicesRepository = utecServicesRepository;
        this.careerRepository = careerRepository;
    }
    @Transactional
    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }
    @Transactional
    public JwtAuthResponse register(RegisterReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        if (req.getRole().equalsIgnoreCase("STUDENT")) {
            Student student = new Student();
            student.setRole(Role.STUDENT);
            student.setName(req.getName());
            student.setEmail(req.getEmail());
            student.setPassword(passwordEncoder.encode(req.getPassword()));
            student.setCareer(careerRepository.findById(req.getCareerId())
                    .orElseThrow(() -> new IllegalArgumentException("Career not found")));

            // Crear StudentFeed con valores por defecto
            StudentPost studentPost = new StudentPost();
            studentPost.setHashtag(""); // Asigna un valor por defecto
            studentPost.setStudent(student); // Establecer la relación con el estudiante

            student.setStudentPost(studentPost);

            studentRepository.save(student);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(student));
            return response;
        } else if (req.getRole().equalsIgnoreCase("CLUB")) {
            Club club = new Club();
            club.setRole(Role.CLUB);
            club.setName(req.getName());
            club.setEmail(req.getEmail());
            club.setPassword(passwordEncoder.encode(req.getPassword()));
            club.setCareer(careerRepository.findById(req.getCareerId()).orElseThrow(() -> new IllegalArgumentException("Career not found")));

            // Crear y asignar una publicación inicial
            ClubPost clubPost = new ClubPost();
            clubPost.setCaption("Default caption");
            clubPost.setMedia("Default media");
            clubPost.setCareer(club.getCareer());
            clubPost.setClub(club);

            List<ClubPost> publications = new ArrayList<>();
            publications.add(clubPost);
            club.setPosts(publications);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(club));
            return response;

        } else if (req.getRole().equalsIgnoreCase("UTEC")) {
            UtecServices utecServices = new UtecServices();
            utecServices.setRole(Role.UTEC);
            utecServices.setName(req.getName());
            utecServices.setEmail(req.getEmail());
            utecServices.setPassword(passwordEncoder.encode(req.getPassword()));

            // Crear y asignar un UtecServicesFeed
            UtecServicesPost utecServicesFeed = new UtecServicesPost();
            utecServicesFeed.setHashtag("defaultHashtag"); // Asigna un valor por defecto o el valor adecuado

            // Establecer la relación bidireccional
            utecServices.setUtecServicesPost(utecServicesFeed);
            utecServicesFeed.setServicesUTEC(utecServices);

            // Guardar las entidades en el orden correcto
            utecServicesRepository.save(utecServices);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(utecServices));
            return response;
        }
        else {
            throw new IllegalArgumentException("Invalid role");
        }
    }
    @Transactional
    public User registerUtecServices(UtecServices user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.UTEC);
        return utecServicesRepository.save(user);
    }
    @Transactional
    public User registerStudent(Student user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STUDENT);
        return studentRepository.save(user);
    }
    @Transactional
    public User registerClub(Club user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CLUB);
        return clubRepository.save(user);
    }

    @Transactional
    public JwtAuthResponse registerAdmin(AdminRegisterReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        User admin = new User();
        admin.setRole(Role.ADMIN);
        admin.setName(req.getName());
        admin.setEmail(req.getEmail());
        admin.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(admin);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(admin));
        return response;
    }

    @Transactional
    public JwtAuthResponse loginAdmin(AdminLoginReq req) throws AccessDeniedException {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        if (!user.getRole().equals(Role.ADMIN)) {
            throw new AccessDeniedException("Not authorized");
        }

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(user));
        return response;
    }
}

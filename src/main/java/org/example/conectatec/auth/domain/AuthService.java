package org.example.conectatec.auth.domain;

import org.e2e.e2e.auth.dto.JwtAuthResponse;
import org.e2e.e2e.auth.dto.LoginReq;
import org.example.conectatec.auth.dto.RegisterReq;

import org.example.conectatec.auth.exceptions.UserAlreadyExistException;
import org.example.conectatec.config.JwtService;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class AuthService {

    private final UserBaseRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthService(UserBaseRepository<User> userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = new ModelMapper();
    }
    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user;
        user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }
    public JwtAuthResponse register(RegisterReq req){
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        if (req.getIsDriver()) {
            Driver driver = new Driver();
            driver.setCategory(req.getCategory());
            driver.setVehicle(modelMapper.map(req.getVehicle(), Vehicle.class));
            driver.setTrips(0);
            driver.setAvgRating(0f);
            driver.setCreatedAt(ZonedDateTime.now());
            driver.setRole(Role.DRIVER);
            driver.setFirstName(req.getFirstName());
            driver.setLastName(req.getLastName());
            driver.setEmail(req.getEmail());
            driver.setPassword(passwordEncoder.encode(req.getPassword()));
            driver.setPhoneNumber(req.getPhone());

            userRepository.save(driver);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(driver));
            return response;
        }
        else {
            Passenger passenger = new Passenger();
            passenger.setCreatedAt(ZonedDateTime.now());
            passenger.setRole(Role.PASSENGER);
            passenger.setFirstName(req.getFirstName());
            passenger.setLastName(req.getLastName());
            passenger.setEmail(req.getEmail());
            passenger.setPassword(passwordEncoder.encode(req.getPassword()));
            passenger.setPhoneNumber(req.getPhone());
            passenger.setAvgRating(0f);
            passenger.setTrips(0);

            userRepository.save(passenger);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(jwtService.generateToken(passenger));
            return response;
        }

    }
}
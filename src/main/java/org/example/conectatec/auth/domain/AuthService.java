package org.example.conectatec.auth.domain;


import org.example.conectatec.auth.dto.JwtAuthResponse;
import org.example.conectatec.auth.dto.LoginReq;
import org.example.conectatec.config.JwtService;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.user.infrastructure.UserBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final BaseUserRepository<User> userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final modelMapper modelMapper;

    @Autowired
    public AuthService(BaseUserRepository<User> UserBaseRepository, JwtService jwtService, PasswordEncoder passwordEncoder, modelMapper modelMapper) {
        this.userRepository = UserBaseRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder; //
        this.modelMapper = modelMapper;
    }

    public JwtAuthResponse Login(LoginReq req) {
        Optional<User> user;
        user = UserBaseRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email id not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse;
        response.setToken(jwtService.generateToken(user.get()));
        return response;

    }


    public JwtAuthResponse register(RegisterReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistsException("Email is already registered");

        //aca registro el usuario y estudiante






        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(user));
        return response;
    }


















}

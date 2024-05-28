package org.example.conectatec.auth.application;

import org.example.conectatec.auth.domain.AuthService;
import org.example.conectatec.auth.dto.JwtAuthResponse;
import org.example.conectatec.auth.dto.LoginReq;
import org.example.conectatec.auth.dto.RegisterReq;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.user.domain.UserService;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        return ResponseEntity.ok(authService.register(req));
    }
    @PostMapping("/utec-services")
    public ResponseEntity<User> registerUtecServices(@RequestBody UtecServices user) {
        return new ResponseEntity<>(userService.registerUtecServices(user), HttpStatus.CREATED);
    }

    @PostMapping("/student")
    public ResponseEntity<User> registerStudent(@RequestBody Student user) {
        return new ResponseEntity<>(userService.registerStudent(user), HttpStatus.CREATED);
    }

    @PostMapping("/club")
    public ResponseEntity<User> registerClub(@RequestBody Club user) {
        return new ResponseEntity<>(userService.registerClub(user), HttpStatus.CREATED);
    }

}

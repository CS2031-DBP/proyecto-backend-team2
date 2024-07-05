package org.example.conectatec.auth.application;

import org.example.conectatec.auth.domain.AuthService;
import org.example.conectatec.auth.dto.*;
import org.example.conectatec.club.domain.Club;
import org.example.conectatec.student.domain.Student;
import org.example.conectatec.user.domain.User;
import org.example.conectatec.utecServices.domain.UtecServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final String SECRET_KEY = "deux_ex_machina";

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthResponse> register(@RequestBody RegisterReq req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/lambda")
    public ResponseEntity<JwtAuthResponse> registerAdmin(@RequestBody AdminRegisterReq req) {
        if (!req.getSecretKey().equals(SECRET_KEY)) {
            throw new AccessDeniedException("Invalid secret key");
        }

        return ResponseEntity.ok(authService.registerAdmin(req));
    }

    @PostMapping("/gamma")
    public ResponseEntity<JwtAuthResponse> loginAdmin(@RequestBody AdminLoginReq req) throws java.nio.file.AccessDeniedException {
        return ResponseEntity.ok(authService.loginAdmin(req));
    }

    @PostMapping("/utec-services")
    public ResponseEntity<User> registerUtecServices(@RequestBody UtecServices user) {
        return new ResponseEntity<>(authService.registerUtecServices(user), HttpStatus.CREATED);
    }

    @PostMapping("/student")
    public ResponseEntity<User> registerStudent(@RequestBody Student user) {
        return new ResponseEntity<>(authService.registerStudent(user), HttpStatus.CREATED);
    }

    @PostMapping("/club")
    public ResponseEntity<User> registerClub(@RequestBody Club user) {
        return new ResponseEntity<>(authService.registerClub(user), HttpStatus.CREATED);
    }

}

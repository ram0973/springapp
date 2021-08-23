package com.me.springapp.controller;

import com.me.springapp.security.payload.LoginRequest;
import com.me.springapp.security.payload.SignupRequest;
import com.me.springapp.service.AuthService;
import com.me.springapp.service.AuthServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//TODO: check this
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private AuthService service;

    @Autowired
    public void setService(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt " + loginRequest.toString());
        return service.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return service.registerUser(signUpRequest);
    }

    @PostMapping("/signup-with-roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUserWithRoles(@Valid @RequestBody SignupRequest signUpRequest) {
        return service.registerUser(signUpRequest);
    }
}

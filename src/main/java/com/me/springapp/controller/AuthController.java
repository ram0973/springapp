package com.me.springapp.controller;

import com.me.springapp.security.payload.LoginRequest;
import com.me.springapp.security.payload.SignupRequest;
import com.me.springapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return service.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return service.registerUser(signUpRequest);
    }

    @PostMapping("/signup-with-roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUserWithRoles(@Valid @RequestBody SignupRequest signUpRequest) {
        return service.registerUserWithRoles(signUpRequest);
    }
}

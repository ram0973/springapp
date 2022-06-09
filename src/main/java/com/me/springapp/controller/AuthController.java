package com.me.springapp.controller;


import com.me.springapp.security.payload.request.LoginRequest;
import com.me.springapp.security.payload.request.SignupRequest;
import com.me.springapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//TODO: check this
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Login attempt " + loginRequest.toString());
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }

    @PostMapping("/signup-with-roles")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUserWithRoles(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}

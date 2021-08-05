package com.me.springapp.service;

import com.me.springapp.security.payload.LoginRequest;
import com.me.springapp.security.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
}

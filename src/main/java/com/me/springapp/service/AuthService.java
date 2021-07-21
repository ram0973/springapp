package com.me.springapp.service;

import com.me.springapp.security.payload.LoginRequest;
import com.me.springapp.security.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest);
    public ResponseEntity<?> registerUserWithRoles(SignupRequest signUpRequest);
}

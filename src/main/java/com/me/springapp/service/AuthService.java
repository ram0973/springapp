package com.me.springapp.service;


import com.me.springapp.security.payload.request.LoginRequest;
import com.me.springapp.security.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);
}

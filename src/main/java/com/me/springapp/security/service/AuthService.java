package com.me.springapp.security.service;


import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> loginUser(LoginRequestDTO loginRequest);
    ResponseEntity<?> registerUser(SignupRequestDTO signUpRequestDTO);
}

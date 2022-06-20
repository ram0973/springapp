package com.me.springapp.security.service;


import com.me.springapp.security.dto.*;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.model.JwtAuthentication;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;

public interface AuthService {
    JwtResponseDTO login(@NonNull JwtRequestDTO authRequest);
    ResponseEntity<?> registerUser(SignupRequestDTO signUpRequestDTO);
    JwtResponseDTO getAccessToken(@NonNull String refreshToken);
    JwtResponseDTO refresh(@NonNull String refreshToken);
    JwtAuthentication getAuthInfo();
}

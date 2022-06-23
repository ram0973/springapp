package com.me.springapp.security.service;


import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.TokensResponseDTO;
import com.me.springapp.security.model.JwtAuthentication;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotBlank;

public interface AuthService {
    TokensResponseDTO login(LoginRequestDTO authRequest);

    ResponseEntity<String> signup(SignupRequestDTO signUpRequestDTO);

    TokensResponseDTO getAccessToken(@NonNull @NotBlank String refreshToken);

    TokensResponseDTO refresh(@NonNull @NotBlank String refreshToken);

    JwtAuthentication getAuthInfo();
}

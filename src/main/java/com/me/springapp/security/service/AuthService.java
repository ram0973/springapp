package com.me.springapp.security.service;


import com.me.springapp.security.dto.AccessTokenResponseDTO;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.AccessAndRefreshTokensResponseDTO;
import com.me.springapp.security.model.JwtAuthentication;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotBlank;

public interface AuthService {
    AccessAndRefreshTokensResponseDTO login(LoginRequestDTO authRequest);

    ResponseEntity<String> signup(SignupRequestDTO signUpRequestDTO);

    AccessTokenResponseDTO getAccessToken(@NonNull @NotBlank String refreshToken);

    AccessAndRefreshTokensResponseDTO refresh(@NonNull @NotBlank String refreshToken);

    JwtAuthentication getAuthInfo();
}

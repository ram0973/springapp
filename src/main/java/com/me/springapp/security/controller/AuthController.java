package com.me.springapp.security.controller;

import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.RefreshTokenRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.TokensResponseDTO;
import com.me.springapp.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<TokensResponseDTO> login(@RequestBody @Valid LoginRequestDTO authRequest) {
        final TokensResponseDTO token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDTO request) {
        return authService.signup(request);
    }

    @PostMapping("token")
    public ResponseEntity<TokensResponseDTO> getNewAccessToken(@RequestBody @Valid RefreshTokenRequestDTO request) {
        final TokensResponseDTO token = authService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<TokensResponseDTO> getNewRefreshToken(@RequestBody @Valid RefreshTokenRequestDTO request) {
        final TokensResponseDTO token = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }

}

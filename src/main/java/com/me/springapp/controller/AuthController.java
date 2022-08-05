package com.me.springapp.controller;

import com.me.springapp.security.dto.*;
import com.me.springapp.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AccessAndRefreshTokensResponseDTO> login(@RequestBody @Valid LoginRequestDTO request) {
        AccessAndRefreshTokensResponseDTO tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("signup")
    public ResponseEntity<AccessAndRefreshTokensResponseDTO> signup(@RequestBody @Valid SignupRequestDTO request) {
        AccessAndRefreshTokensResponseDTO tokens = authService.signup(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("token")
    public ResponseEntity<AccessTokenResponseDTO> getNewAccessToken(
        @RequestBody @Valid @NotNull RefreshTokenRequestDTO request) {
        AccessTokenResponseDTO token = authService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<AccessAndRefreshTokensResponseDTO> getNewRefreshToken(
        @RequestBody @Valid @NotNull RefreshTokenRequestDTO request) {
        AccessAndRefreshTokensResponseDTO token = authService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }

}

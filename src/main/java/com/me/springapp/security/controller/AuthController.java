package com.me.springapp.security.controller;


import com.me.springapp.security.dto.JwtRefreshRequestDTO;
import com.me.springapp.security.dto.JwtRequestDTO;
import com.me.springapp.security.dto.JwtResponseDTO;
import com.me.springapp.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<JwtResponseDTO> login(@RequestBody @Valid JwtRequestDTO authRequest) {
        final JwtResponseDTO token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponseDTO> getNewAccessToken(@RequestBody JwtRefreshRequestDTO request) {
        final JwtResponseDTO token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponseDTO> getNewRefreshToken(@RequestBody JwtRefreshRequestDTO request) {
        final JwtResponseDTO token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}

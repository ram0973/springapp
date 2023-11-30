package com.me.springapp.controller;

import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.service.AuthService;
import com.me.springapp.security.service.AuthenticationProviderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationProviderService authenticationProviderService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO request,
                                   HttpServletRequest httpServletRequest, HttpServletResponse response) {
        authService.login(request);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
            request.email(), request.password());
        authenticationProviderService.authenticate(token);
        httpServletRequest.getSession(true);
        return ResponseEntity.ok().build();
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDTO request) {
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
}

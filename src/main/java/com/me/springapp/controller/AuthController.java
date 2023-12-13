package com.me.springapp.controller;

import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.service.AuthService;
import com.me.springapp.security.service.AuthenticationProviderService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

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
        Authentication authentication = authenticationProviderService.authenticate(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        log.info(securityContext.getAuthentication().getPrincipal().toString());

        // Create a new session and add the security context.
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        Cookie cookie = new Cookie("X-Auth-Token", session.getId());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 365);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequestDTO request) {
        authService.signup(request);
        return ResponseEntity.ok().build();
    }
}

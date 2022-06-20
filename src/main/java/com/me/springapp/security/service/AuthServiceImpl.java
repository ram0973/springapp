package com.me.springapp.security.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.exceptions.WrongEmailOrPasswordException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.security.dto.*;
import com.me.springapp.security.model.JwtAuthentication;
import com.me.springapp.security.provider.JwtProvider;
import com.me.springapp.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements com.me.springapp.security.service.AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final String jwtSecret;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationProvider authenticationProvider,
                           UserService userService,
                           @Value("${springapp.jwtSecret}") String jwtSecret,
                           PasswordEncoder passwordEncoder,
                           JwtProvider jwtProvider) {
        this.authenticationProvider = authenticationProvider;
        this.userService = userService;
        this.jwtSecret = jwtSecret;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponseDTO login(@NonNull JwtRequestDTO authRequest) {
        final User user = userService.findUserByEmailIgnoreCase(authRequest.getEmail())
            .orElseThrow(NoSuchUserException::new);
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponseDTO(accessToken, refreshToken);
        } else {
            throw new WrongEmailOrPasswordException();
        }
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequestDTO signUpRequest) {
        userService.findUserByEmailIgnoreCase(signUpRequest.email()).orElseThrow(EmailAlreadyInUseException::new);
        Set<Role> roles = new HashSet<>() {{
            add(Role.ROLE_USER);
        }};
        User user = new User(
            ModelState.ENABLED,
            LocalDateTime.now(),
            signUpRequest.email(),
            passwordEncoder.encode(signUpRequest.password()),
            null,
            roles);
        userService.saveUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public JwtResponseDTO getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(email);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email)
                    .orElseThrow(NoSuchUserException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponseDTO(accessToken, null);
            }
        }
        return new JwtResponseDTO(null, null);
    }

    public JwtResponseDTO refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email)
                    .orElseThrow(NoSuchUserException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponseDTO(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Invalid JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}

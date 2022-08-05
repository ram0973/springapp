package com.me.springapp.security.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.InvalidJWTException;
import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.UserState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.security.dto.AccessTokenResponseDTO;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.AccessAndRefreshTokensResponseDTO;
import com.me.springapp.security.model.JwtAuthentication;
import com.me.springapp.security.provider.JwtProvider;
import com.me.springapp.service.UserService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements com.me.springapp.security.service.AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public AccessAndRefreshTokensResponseDTO login(
        @NotNull LoginRequestDTO authRequest) {
        final User user = userService.findUserByEmailIgnoreCase(authRequest.email()).orElseThrow(
            () -> new NoSuchEntityException("No such user with email: " + authRequest.email()));
        if (passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new AccessAndRefreshTokensResponseDTO(accessToken, refreshToken, user.getEmail());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public AccessAndRefreshTokensResponseDTO signup(@NonNull SignupRequestDTO signUpRequest) {
        Optional<User> userOptional = userService.findUserByEmailIgnoreCase(signUpRequest.email());
        if (userOptional.isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        User user = new User();
        user.setState(UserState.ENABLED);
        user.setDateCreated(LocalDateTime.now());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setArticles(null);
        user.setRoles(new HashSet<>(Set.of(Role.ROLE_USER)));

        userService.saveUser(user);
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);
        return new AccessAndRefreshTokensResponseDTO(accessToken, refreshToken, user.getEmail());
    }

    public AccessTokenResponseDTO getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(email);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(
                    () -> new NoSuchEntityException("No such user with email: " + email));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new AccessTokenResponseDTO(accessToken);
            }
        }
        throw new InvalidJWTException("");
    }

    public AccessAndRefreshTokensResponseDTO refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(
                    () -> new NoSuchEntityException("No such user with email " + email));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new AccessAndRefreshTokensResponseDTO(accessToken, newRefreshToken, user.getEmail());
            }
        }
        // TODO: check this
        throw new InvalidJWTException("Invalid JWT refresh token: " + refreshToken);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}

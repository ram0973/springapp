package com.me.springapp.service;

import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.model.User;
import com.me.springapp.security.dto.JwtRequestDTO;
import com.me.springapp.security.dto.JwtResponseDTO;
import com.me.springapp.security.model.JwtAuthentication;
import com.me.springapp.security.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponseDTO login(@NonNull JwtRequestDTO authRequest) throws AuthException {
        final User user = userService.findUserByEmailIgnoreCase(authRequest.getEmail()).orElseThrow(NoSuchUserException::new);
        if (user.getEmail().equals(authRequest.getEmail())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponseDTO(accessToken, refreshToken);
        } else {
            throw new AuthException("Wrong email or password");
        }
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

    public JwtResponseDTO refresh(@NonNull String refreshToken) throws AuthException {
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
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}

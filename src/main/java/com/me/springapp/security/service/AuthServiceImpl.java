package com.me.springapp.security.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.TokensResponseDTO;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements com.me.springapp.security.service.AuthService {
    private final AuthenticationProvider authenticationProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public TokensResponseDTO login(@NotNull LoginRequestDTO authRequest) {
        final User user = userService.findUserByEmailIgnoreCase(authRequest.email())
            .orElseThrow(NoSuchUserException::new);
        if (passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new TokensResponseDTO(accessToken, refreshToken);
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public ResponseEntity<String> signup(@NonNull SignupRequestDTO signUpRequest) {
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

    public TokensResponseDTO getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String savedRefreshToken = refreshStorage.get(email);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(NoSuchUserException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new TokensResponseDTO(accessToken, null);
            }
        }
        return new TokensResponseDTO(null, null);
    }

    public TokensResponseDTO refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(NoSuchUserException::new);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new TokensResponseDTO(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Invalid JWT token");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}

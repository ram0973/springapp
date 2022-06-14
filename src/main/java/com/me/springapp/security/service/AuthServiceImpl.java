package com.me.springapp.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.security.dto.TokensDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class AuthServiceImpl implements com.me.springapp.security.service.AuthService {
    private final AuthenticationProvider authenticationProvider;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final String jwtSecret;

    @Autowired
    public AuthServiceImpl(AuthenticationProvider authenticationProvider, UserRepository userRepository,
                           @Value("${springapp.jwtSecret}") String jwtSecret, PasswordEncoder passwordEncoder) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
        this.jwtSecret = jwtSecret;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        if (authentication == null) {
            log.error("Authentication object is null");
            throw new RuntimeException("Authentication object is null");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(NoSuchUsersException::new);

        Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());
        String accessToken = JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
            .sign(algorithm);

        String refreshToken = JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
            .sign(algorithm);

        var payload = new TokensDTO(accessToken, refreshToken);
        return ResponseEntity.ok().body(payload);
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequestDTO signUpRequest) {
        if (userRepository.existsByEmailIgnoreCase(signUpRequest.email())) {
            throw new EmailAlreadyInUseException();
        }
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
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}

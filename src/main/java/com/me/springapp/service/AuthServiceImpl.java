package com.me.springapp.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.UsernameAlreadyTakenException;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import com.me.springapp.security.jwt.JwtUtils;
import com.me.springapp.security.payload.JwtResponse;
import com.me.springapp.security.payload.LoginRequest;
import com.me.springapp.security.payload.MessageResponse;
import com.me.springapp.security.payload.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
            jwtToken,
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.username())) {
            throw new UsernameAlreadyTakenException();
        }

        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new EmailAlreadyInUseException();
        }

        Set<String> requestRoles = signUpRequest.roles();
        Set<Role> roles = new HashSet<>();

        if (requestRoles == null) {
            roles.add(new Role(RoleEnum.ROLE_USER));
        } else {
            for (String role : requestRoles) {
                switch (role) {
                    case "admin" -> roles.add(new Role(RoleEnum.ROLE_ADMIN));
                    case "mod" -> roles.add(new Role(RoleEnum.ROLE_MODERATOR));
                    default -> roles.add(new Role(RoleEnum.ROLE_USER));
                }
            }
        }
        User user = new User(
            signUpRequest.username(),
            signUpRequest.email(),
            encoder.encode(signUpRequest.password()),
            User.USER_ACTIVE,
            LocalDateTime.now(),
            roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}

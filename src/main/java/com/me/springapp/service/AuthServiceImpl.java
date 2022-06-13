package com.me.springapp.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.UsernameAlreadyTakenException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import com.me.springapp.security.userdetails.UserDetailsImpl;
import com.me.springapp.security.jwt.JwtUtils;
import com.me.springapp.security.payload.request.LoginRequest;
import com.me.springapp.security.payload.request.SignupRequest;
import com.me.springapp.security.payload.response.JwtResponse;
import com.me.springapp.security.payload.response.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    //private final JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImpl(AuthenticationProvider authenticationProvider, UserRepository userRepository,
                           PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationProvider = authenticationProvider;
        this.userRepository = userRepository;
        this.encoder = encoder;
        //this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationProvider.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>(HttpStatus.OK);


        //String jwtToken = jwtUtils.generateJwtToken(authentication);

        //UserDetailsImpl userDetails = authentication.getPrincipal();
        //List<String> roles = userDetails
        //    .getAuthorities()
        //    .stream()
        //    .map(GrantedAuthority::getAuthority).toList();

        //return null; //ResponseEntity.ok(new JwtResponse(
            //jwtToken,
            //userDetails.getUsername(),
            //roles));
    }

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail())) {
            throw new UsernameAlreadyTakenException();
        }

        if (userRepository.existsByEmailIgnoreCase(signUpRequest.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        Set<String> requestRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (requestRoles == null) {
            roles.add(Role.ROLE_USER);
        } else {
            for (String role : requestRoles) {
                switch (role) {
                    case "admin" -> roles.add(Role.ROLE_ADMIN);
                    case "mod" -> roles.add(Role.ROLE_MODERATOR);
                    default -> roles.add(Role.ROLE_USER);
                }
            }
        }
        User user = new User(
            ModelState.ENABLED,
            LocalDateTime.now(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()),
            null,
            roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}

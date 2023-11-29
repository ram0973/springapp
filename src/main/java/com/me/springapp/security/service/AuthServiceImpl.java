package com.me.springapp.security.service;

import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.model.UserState;
import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import com.me.springapp.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements com.me.springapp.security.service.AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProviderService authenticationProviderService;
    private final AuthenticationProvider authenticationProvider;

    public void login(
        @NotNull LoginRequestDTO authRequest) {
        final User user = userService.findUserByEmailIgnoreCase(authRequest.email()).orElseThrow(
            () -> new NoSuchEntityException("No such user with email: " + authRequest.email()));
        if (passwordEncoder.matches(authRequest.password(), user.getPassword())) {

        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public void signup(@NonNull SignupRequestDTO signUpRequest) {
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
    }

    public Authentication getAuthInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

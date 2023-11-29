package com.me.springapp.security.service;

import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.User;
import com.me.springapp.security.userdetails.UserDetailsImpl;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    private @NotNull Authentication checkPassword(@NotNull UserDetailsImpl user,
                                                  String rawPassword,
                                                  @NotNull PasswordEncoder encoder) {
        if (encoder.matches(rawPassword, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public Authentication authenticate(@NotNull Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(
            () -> new NoSuchEntityException("No such user with email: " + email));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication userAuthentication = this.checkPassword(userDetails, password, passwordEncoder);
        if (userAuthentication.isAuthenticated()) {
            return userAuthentication;
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authClass);
    }
}


package com.me.springapp.security.service;

import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.model.User;
import com.me.springapp.security.userdetails.UserDetailsImpl;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private Authentication checkPassword(UserDetailsImpl user,
                                         String rawPassword,
                                         PasswordEncoder encoder) {
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
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        User user = userService.findUserByEmailIgnoreCase(email).orElseThrow(NoSuchUserException::new);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        Authentication resultAuthentication = checkPassword(userDetails, password, passwordEncoder);
        if (resultAuthentication.isAuthenticated()) {
            return resultAuthentication;
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authClass);
    }
}


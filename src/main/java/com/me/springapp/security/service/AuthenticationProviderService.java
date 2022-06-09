package com.me.springapp.security.service;

import com.me.springapp.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.me.springapp.model.PasswordCipher.BCRYPT;
import static com.me.springapp.model.PasswordCipher.SCRYPT;

@Service
public class AuthenticationProviderService  implements AuthenticationProvider {


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SCryptPasswordEncoder sCryptPasswordEncoder;

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
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByEmail(email);
        switch (userDetails.getUser().getCipher()) {
            case BCRYPT:
                return checkPassword(userDetails, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(userDetails, password, sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authClass);
    }
}


package com.me.springapp.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticationManager { //implements AuthenticationManager {
//    private final AuthenticationProviderService authenticationProviderService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        return authenticationProviderService.authenticate(authentication);
//    }
}

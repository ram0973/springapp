package com.me.springapp.security.service;

import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.dto.SignupRequestDTO;
import org.springframework.security.core.Authentication;

public interface AuthService {
    void login(LoginRequestDTO authRequest);

    void signup(SignupRequestDTO signUpRequestDTO);

    Authentication getAuthInfo();
}

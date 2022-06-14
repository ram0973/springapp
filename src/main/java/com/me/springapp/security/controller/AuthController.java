package com.me.springapp.security.controller;


import com.me.springapp.security.dto.LoginRequestDTO;
import com.me.springapp.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return authService.loginUser(loginRequest);
    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody com.me.springapp.security.dto.SignupRequestDTO signUpRequestDTO) {
//        return authService.registerUser(signUpRequestDTO);
//    }
//
//    @PostMapping("/signup-with-roles")
//    //@PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> registerUserWithRoles(@Valid @RequestBody com.me.springapp.security.dto.SignupRequestDTO signUpRequestDTO) {
//        return authService.registerUser(signUpRequestDTO);
//    }
}

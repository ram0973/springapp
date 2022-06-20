package com.me.springapp.security.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequestDTO {
    private String email;
    private String password;
}

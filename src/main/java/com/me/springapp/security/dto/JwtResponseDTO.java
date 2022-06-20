package com.me.springapp.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDTO {

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

}

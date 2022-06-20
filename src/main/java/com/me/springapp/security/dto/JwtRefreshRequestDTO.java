package com.me.springapp.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRefreshRequestDTO {
    public String refreshToken;
}

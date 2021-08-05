package com.me.springapp.security.payload;

import lombok.Getter;

import java.util.List;

public record JwtResponse(String accessToken, int userId, String username, String email, List<String> roles) {
    @Getter
    private static final String tokenType = "Bearer";
}

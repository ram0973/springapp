package com.me.springapp.security.payload;

import java.util.List;

public record JwtResponse(String accessToken, int userId, String username, String email, List<String> roles) {
    private static final String tokenType = "Bearer";
}

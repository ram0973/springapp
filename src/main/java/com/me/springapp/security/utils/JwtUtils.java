package com.me.springapp.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.me.springapp.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtils {

    @Value("${springapp.jwtSecret}")
    static String jwtSecret;
    private static Algorithm algorithm = Algorithm.HMAC256(jwtSecret.getBytes());

    public String generateAccessToken(User user) {
        return JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
            .sign(algorithm);
    }

    public static String generateRefreshToken(User user) {
        return JWT.create()
            .withSubject(user.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withClaim("roles", user.getRoles().stream().map(Enum::name).toList())
            .sign(algorithm);
    }
}

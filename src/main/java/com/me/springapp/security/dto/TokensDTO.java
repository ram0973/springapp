package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record TokensDTO(@NotBlank String accessToken, @NotBlank String refreshToken) {
    @Builder
    public TokensDTO {}
}

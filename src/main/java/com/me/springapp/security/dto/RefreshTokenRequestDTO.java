package com.me.springapp.security.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequestDTO(
    @NotBlank @NotNull String refreshToken
) {
    @Builder
    public RefreshTokenRequestDTO {
    }
}


package com.me.springapp.security.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshTokenResponseDTO(@NotBlank @NotNull String refreshToken) {
    @Builder
    public RefreshTokenResponseDTO {
        // builder by Lombok
    }
}
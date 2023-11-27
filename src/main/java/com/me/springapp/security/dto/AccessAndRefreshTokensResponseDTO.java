package com.me.springapp.security.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccessAndRefreshTokensResponseDTO(
    @NotBlank @NotNull String accessToken,
    @NotBlank @NotNull String refreshToken,
    @NotBlank @NotNull String email) {
    @Builder
    public AccessAndRefreshTokensResponseDTO {
        // builder by Lombok
    }
}
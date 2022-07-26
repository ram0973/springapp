package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AccessAndRefreshTokensResponseDTO(
    @NotBlank @NotNull String accessToken,
    @NotBlank @NotNull String refreshToken) {
    @Builder
    public AccessAndRefreshTokensResponseDTO {
        // builder by Lombok
    }
}
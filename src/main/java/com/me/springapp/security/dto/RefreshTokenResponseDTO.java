package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RefreshTokenResponseDTO(@NotBlank @NotNull String refreshToken) {
    @Builder
    public RefreshTokenResponseDTO {
        // builder by Lombok
    }
}
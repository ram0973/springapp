package com.me.springapp.security.dto;

import lombok.Builder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccessTokenResponseDTO(@NotBlank @NotNull String accessToken) {
    @Builder
    public AccessTokenResponseDTO {
        // builder by Lombok
    }
}
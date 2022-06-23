package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record RefreshTokenRequestDTO(
    @NotBlank @NotNull String refreshToken
) {
    @Builder
    public RefreshTokenRequestDTO {
    }
}


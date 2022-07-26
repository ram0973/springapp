package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record AccessTokenResponseDTO(@NotBlank @NotNull String accessToken) {
    @Builder
    public AccessTokenResponseDTO {
        // builder by Lombok
    }
}
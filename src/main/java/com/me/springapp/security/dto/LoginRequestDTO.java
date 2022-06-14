package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String email, @NotBlank String password) {
    @Builder
    public LoginRequestDTO {}
}


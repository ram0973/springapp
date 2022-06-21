package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record LoginRequestDTO(
    @NotBlank @NotNull @Email String email,
    @NotBlank @NotNull String password) {
    @Builder
    public LoginRequestDTO {}
}


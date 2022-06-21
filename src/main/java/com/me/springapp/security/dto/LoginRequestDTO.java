package com.me.springapp.security.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record LoginRequestDTO(
    @NotBlank @NotNull @Email @Size(max = 50) String email,
    @NotBlank @NotNull @Size(min = 6, max = 40) String password) {
    @Builder
    public LoginRequestDTO {
    }
}


package com.me.springapp.security.dto;

import lombok.Builder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignupRequestDTO(
    @NotBlank @Size(max = 50) @Email String email,
    @NotBlank @NotNull @Size(min = 6, max = 40) String password
) {
    @Builder
    public SignupRequestDTO {
    }
}
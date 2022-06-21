package com.me.springapp.security.dto;

import lombok.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.util.Set;

public record SignupRequestDTO (
    @NotBlank @Size(max = 50) @Email String email,
    @Enumerated(EnumType.STRING) Set<String> role,
    @NotBlank @Size(min = 6, max = 40) String password
) {
   @Builder
   public SignupRequestDTO {}
}
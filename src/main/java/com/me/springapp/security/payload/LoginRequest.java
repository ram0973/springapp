package com.me.springapp.security.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank @Getter String username, @NotBlank @Getter String password) {
}

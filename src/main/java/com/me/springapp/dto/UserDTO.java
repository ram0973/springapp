package com.me.springapp.dto;

import com.me.springapp.model.UserState;
import com.me.springapp.model.Role;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public record UserDTO(
    @Enumerated(EnumType.STRING) UserState state,
    @DateTimeFormat String dateCreated,
    @NotNull @NotBlank String email,
    @NotNull String password,
    Set<Role> roles
) {

    @Builder
    public UserDTO {
    }
}

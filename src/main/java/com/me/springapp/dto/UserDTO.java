package com.me.springapp.dto;

import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import lombok.Builder;

import java.util.Set;

public record UserDTO(
    ModelState modelState,
    String dateCreated,
    String username,
    String email,
    String password,
    boolean active,
    Set<Role> roles
) {

    @Builder
    public UserDTO {
    }
}

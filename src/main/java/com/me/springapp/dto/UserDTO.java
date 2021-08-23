package com.me.springapp.dto;

import com.me.springapp.model.Role;
import lombok.Builder;

import java.util.Set;

public record UserDTO(
    String username,
    String email,
    String password,
    boolean active,
    String dateCreated,
    Set<Role> roles
) {

    @Builder
    public UserDTO {
    }
}

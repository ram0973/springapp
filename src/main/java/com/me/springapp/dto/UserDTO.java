package com.me.springapp.dto;

import com.me.springapp.model.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserDTO(
    int id,
    String username,
    String email,
    String password,
    boolean active,
    LocalDateTime dateCreated,
    Set<Role> roles
) {
}

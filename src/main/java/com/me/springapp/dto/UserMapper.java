package com.me.springapp.dto;

import com.me.springapp.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMapper {

    static final String dateTimeFormat = "dd-MM-yyyy HH:mm";

    public static UserDTO userToDto(User user) {
        return UserDTO.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .active(user.isActive())
            .dateCreated(user.getDateCreated().format(DateTimeFormatter.ofPattern(dateTimeFormat)))
            .roles(user.getRoles())
            .build();
    }

    public static User userFromDto(UserDTO userDTO) {
        return User.builder()
            .username(userDTO.username())
            .email(userDTO.email())
            .password(userDTO.password())
            .active(userDTO.active())
            .dateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)))
            .roles(userDTO.roles())
            .build();
    }

    public static void updateUserFromDto(User user, UserDTO userDTO) {
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setActive(userDTO.active());
        user.setDateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)));
        user.setRoles(userDTO.roles());
    }
}

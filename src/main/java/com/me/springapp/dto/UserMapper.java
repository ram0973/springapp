package com.me.springapp.dto;

import com.me.springapp.model.User;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMapper {

    static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";

    public static UserDTO userToDto(@NotNull User user) {
        return UserDTO.builder()
            .state(user.getState())
            .email(user.getEmail())
            .password(user.getPassword())
            .dateCreated(user.getDateCreated().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
            .roles(user.getRoles())
            .build();
    }

    public static User userFromDto(@NotNull UserDTO userDTO) {
        return User.builder()
            .state(userDTO.state())
            .email(userDTO.email())
            .password(userDTO.password())
            .dateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)))
            .roles(userDTO.roles())
            .build();
    }

    public static void updateUserFromDto(@NotNull User user, @NotNull UserDTO userDTO) {
        user.setState(userDTO.state());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setDateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        user.setRoles(userDTO.roles());
    }
}

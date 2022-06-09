package com.me.springapp.dto;

import com.me.springapp.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserMapper {

    static final String dateTimeFormat = "dd-MM-yyyy HH:mm";

    public static UserDTO userToDto(User user) {
        return UserDTO.builder()
            .modelState(user.getState())
            .email(user.getEmail())
            .password(user.getPassword())
            .dateCreated(user.getDateCreated().format(DateTimeFormatter.ofPattern(dateTimeFormat)))
            .roles(user.getRoles())
            .build();
    }

    public static User userFromDto(UserDTO userDTO) {
        return User.builder()
            .state(userDTO.modelState())
            .email(userDTO.email())
            .password(userDTO.password())
            .dateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)))
            .roles(userDTO.roles())
            .build();
    }

    public static void updateUserFromDto(User user, UserDTO userDTO) {
        user.setState(userDTO.modelState());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setDateCreated(LocalDateTime.parse(userDTO.dateCreated(), DateTimeFormatter.ofPattern(dateTimeFormat)));
        user.setRoles(userDTO.roles());
    }
}

package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.model.UserState;

import java.util.Optional;

public interface UserService {
    Optional<User> saveUser(User user);

    void addRoleToUser(String userName, Role role);

    Optional<PagedUsersDTO> findAll(int page, int size, String[] sort);

    Optional<PagedUsersDTO> findAllByState(int page, int size, String[] sort, UserState state);

    Optional<User> findById(int id);

    Optional<User> findUserByEmailIgnoreCase(String email);

    Optional<User> findByIdAndState(int id, UserState state);

    Optional<User> createUser(UserDTO userDTO);

    Optional<User> updateUser(int id, UserDTO userDTO);

    void deleteUser(int id);

    Optional<User> softDeleteUser(int id);
}

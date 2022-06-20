package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String userName, Role role);
    ResponseEntity<PagedUsersDTO> findAll(int page, int size, String[] sort);
    ResponseEntity<PagedUsersDTO> findAllByState(int page, int size, String[] sort, ModelState state);
    ResponseEntity<User> findById(int id);
    Optional<User> findUserByEmailIgnoreCase(String email);
    ResponseEntity<User> findByIdAndState(int id, ModelState state);
    ResponseEntity<User> createUser(UserDTO userDTO);
    ResponseEntity<User> updateUser(int id, UserDTO userDTO);
    ResponseEntity<HttpStatus> deleteUser(int id);
    ResponseEntity<HttpStatus> softDeleteUser(int id);
}

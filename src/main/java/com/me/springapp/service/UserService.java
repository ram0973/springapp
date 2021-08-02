package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.model.Role;
import com.me.springapp.model.RoleEnum;
import com.me.springapp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    public void addRoleToUser(String userName, RoleEnum roleName);
    ResponseEntity<PagedUsersDTO> findAll(String username, int page, int size, String[] sort);
    ResponseEntity<User> findById(int id);
    ResponseEntity<User> createUser(User user);
    ResponseEntity<User> updateUser(int id, User user);
    ResponseEntity<HttpStatus> deleteUser(int id);
    ResponseEntity<PagedUsersDTO> findAllByActive(String username, int page, int size, String[] sort);
}

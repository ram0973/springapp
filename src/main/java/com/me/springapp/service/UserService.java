package com.me.springapp.service;

import com.me.springapp.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    ResponseEntity<List<User>> findAll();
    ResponseEntity<User> findById(int id);
    ResponseEntity<User> createUser(User user);
    ResponseEntity<User> updateUser(int id, User user);
    ResponseEntity<HttpStatus> deleteUser(int id);
    ResponseEntity<List<User>> findByActive();
}

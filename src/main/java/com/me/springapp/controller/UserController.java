package com.me.springapp.controller;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.model.User;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

//TODO: check this
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedUsersDTO> getArticles(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        return userService.findAll(page, size, sort);
    }

    @GetMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @PostMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PutMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        return userService.deleteUser(id);
    }

    @DeleteMapping("/users/soft/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> softDeleteUser(@PathVariable("id") int id) {
        return userService.softDeleteUser(id);
    }

    @GetMapping("/users/active")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedUsersDTO> findAllByActive(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        return userService.findAllByActive(page, size, sort);
    }
}

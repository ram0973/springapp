package com.me.springapp.controller;

import com.me.springapp.dto.PagedArticlesDTO;
import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.exceptions.NoSuchArticleException;
import com.me.springapp.exceptions.NoSuchArticlesException;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.Article;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.User;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedUsersDTO> getUsers(
        @RequestParam(required = false) String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        PagedUsersDTO pagedUsersDTO = userService.findAll(page, size, sort)
            .orElseThrow(NoSuchUsersException::new);
        return ResponseEntity.ok(pagedUsersDTO);
    }

    @GetMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.findById(id).orElseThrow(NoSuchUserException::new);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) {
        // TODO: server error exception?
        User user = userService.createUser(userDTO).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) {
        // TODO: server error exception?
        User user = userService.updateUser(id, userDTO).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        // TODO: server error exception?
        userService.deleteUser(id);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/users/soft-delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> softDeleteUser(@PathVariable("id") int id) {
        // TODO: server error exception?
        User user = userService.softDeleteUser(id).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/active")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedUsersDTO> findAllByActive(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        PagedUsersDTO pagedUsersDTO = userService.findAllByState(page, size, sort, ModelState.ENABLED)
            .orElseThrow(NoSuchUsersException::new);
        return ResponseEntity.ok(pagedUsersDTO);
    }
}

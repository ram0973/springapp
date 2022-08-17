package com.me.springapp.controller;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.exceptions.EmailAlreadyInUseException;
import com.me.springapp.exceptions.EntityPersistException;
import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.UserState;
import com.me.springapp.model.User;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
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
        PagedUsersDTO pagedUsersDTO = userService.findAll(page, size, sort).orElseThrow(
            () -> new NoSuchEntityException("No such users"));
        return ResponseEntity.ok(pagedUsersDTO);
    }

    @GetMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.findById(id).orElseThrow(
            () -> new NoSuchEntityException("No such user with id: " + id));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@Valid @RequestBody @NotNull UserDTO userDTO) {
        Optional<User> optionalUser = userService.findUserByEmailIgnoreCase(userDTO.email());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        } else {
            User user = userService.createUser(userDTO).orElseThrow(
                () -> new EntityPersistException("Error while create user: " + userDTO));
            return ResponseEntity.ok(user);
        }
    }

    @PutMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @Valid @RequestBody UserDTO userDTO) {
        User user = userService.updateUser(id, userDTO).orElseThrow(
            () -> new EntityPersistException(
                String.format("Error while update user with id: %d and body: %s", id, userDTO)));
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        // JPA Repository throws EmptyResultDataAccessException if such id is not exist
        userService.deleteUser(id);

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/users/soft-delete/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> softDeleteUser(@PathVariable("id") int id) {
        User user = userService.softDeleteUser(id).orElseThrow(
            () -> new EntityPersistException("Error while soft delete user with id: " + id));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/active")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagedUsersDTO> findAllByActive(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,desc") String[] sort
    ) {
        PagedUsersDTO pagedUsersDTO = userService.findAllByState(page, size, sort, UserState.ENABLED)
            .orElseThrow(() -> new NoSuchEntityException("No such users"));
        return ResponseEntity.ok(pagedUsersDTO);
    }
}

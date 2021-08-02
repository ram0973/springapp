package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.exceptions.NoSuchRoleException;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.Role;
import com.me.springapp.model.RoleEnum;
import com.me.springapp.model.User;
import com.me.springapp.repository.RoleRepository;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, RoleEnum roleName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isEmpty()) {
            throw new NoSuchUserException();
        }
        Optional<Role> role = roleRepository.findByName(roleName);
        if (role.isEmpty()) {
            throw new NoSuchRoleException();
        }
        User loadedUser = user.get();
        Set<Role> roles = loadedUser.getRoles();
        roles.add(role.get());
        loadedUser.setRoles(roles);
        userRepository.save(loadedUser);
    }

    @Override
    public ResponseEntity<PagedUsersDTO> findAll(String username, int page, int size, String[] sort) {
        List<User> users;
        users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new NoSuchUsersException();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity<User> createUser(User user) {
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUser(int id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(int id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(updatedUser.isActive());
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<PagedUsersDTO> findAllByActive(String username, int page, int size, String[] sort) {
        List<User> users = userRepository.findByActive(User.USER_ACTIVE);
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}

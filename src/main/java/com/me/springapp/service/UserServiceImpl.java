package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.dto.UserMapper;
import com.me.springapp.exceptions.NoSuchArticleException;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.exceptions.NoSuchUsersException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private ResponseEntity<PagedUsersDTO> getPagedUsersDTOResponseEntity(Page<User> pagedUsers) {
        List<User> users = pagedUsers.getContent();
        if (users.isEmpty()) {
            throw new NoSuchUsersException();
        } else {
            PagedUsersDTO response = new PagedUsersDTO(users, pagedUsers.getNumber(),
                pagedUsers.getTotalElements(), pagedUsers.getTotalPages());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String email, Role role) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(NoSuchUserException::new);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public ResponseEntity<PagedUsersDTO> findAll(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<User> pagedUsers = userRepository.findAll(pageable);
        return getPagedUsersDTOResponseEntity(pagedUsers);
    }

    @Override
    public ResponseEntity<PagedUsersDTO> findAllByState(int page, int size, String[] sort, ModelState state) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<User> pagedUsers;
        pagedUsers = userRepository.findAllByState(pageable, state);
        return getPagedUsersDTOResponseEntity(pagedUsers);
    }

    @Override
    public ResponseEntity<User> findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseThrow(NoSuchUserException::new);
    }

    @Override
    public Optional<User> findUserByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public ResponseEntity<User> findByIdAndState(int id, ModelState state) {
        Optional<User> user = userRepository.findByIdAndState(id, state);
        return user
            .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
            .orElseThrow(NoSuchUserException::new);
    }

    @Override
    public ResponseEntity<User> createUser(UserDTO userDTO) {
        User user = UserMapper.userFromDto(userDTO);
        User savedUser = userRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteUser(int id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> softDeleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(NoSuchArticleException::new);
        user.setState(ModelState.SOFT_DELETED);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> updateUser(int id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
        UserMapper.updateUserFromDto(user, userDTO);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);

    }
}

package com.me.springapp.service;

import com.me.springapp.dto.PagedUsersDTO;
import com.me.springapp.dto.UserDTO;
import com.me.springapp.dto.UserMapper;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.model.ModelState;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Optional<PagedUsersDTO> getPagedUsersDTOResponseEntity(Page<User> pagedUsers) {
        List<User> users = pagedUsers.getContent();
        if (users.isEmpty()) {
            return Optional.empty();
        } else {
            PagedUsersDTO pagedUsersDTO = new PagedUsersDTO(users, pagedUsers.getNumber(), pagedUsers.getTotalElements(), pagedUsers.getTotalPages());
            return Optional.of(pagedUsersDTO);
        }
    }

    @Override
    public Optional<User> saveUser(User user) {
        return Optional.of(userRepository.save(user));
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
    public Optional<PagedUsersDTO> findAll(int page, int size, String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<User> pagedUsers = userRepository.findAll(pageable);
        return getPagedUsersDTOResponseEntity(pagedUsers);
    }

    @Override
    public Optional<PagedUsersDTO> findAllByState(int page, int size, String[] sort, ModelState state) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(PagedEntityUtils.getSortOrders(sort)));
        Page<User> pagedUsers;
        pagedUsers = userRepository.findAllByState(pageable, state);
        return getPagedUsersDTOResponseEntity(pagedUsers);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmailIgnoreCase(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public Optional<User> findByIdAndState(int id, ModelState state) {
        return userRepository.findByIdAndState(id, state);
    }

    @Override
    public Optional<User> createUser(UserDTO userDTO) {
        User user = UserMapper.userFromDto(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> softDeleteUser(int id) {
        User user = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
        user.setState(ModelState.SOFT_DELETED);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> updateUser(int id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(NoSuchUserException::new);
        UserMapper.updateUserFromDto(user, userDTO);
        return Optional.of(userRepository.save(user));
    }
}

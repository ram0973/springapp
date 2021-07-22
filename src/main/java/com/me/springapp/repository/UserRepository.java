package com.me.springapp.repository;

import com.me.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    List<User> findByActive(boolean active);
    Optional<User> findByUsername(String username);
    Optional<User> findUserByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);
    //Page<User> findByActive(boolean active, Pageable pageable);
    Page<User> findByUsernameContaining(String username, Pageable pageable);

}

package com.me.springapp.repository;

import com.me.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<User> findAllByActiveIsTrue(@NonNull Pageable pageable);
}

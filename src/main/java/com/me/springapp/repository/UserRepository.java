package com.me.springapp.repository;

import com.me.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    // HQL query example
    @RestResource(rel = "by-email", path = "by-email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findById(int id);

    Optional<User> findByUsername(String username);

    @RestResource(rel = "by-username", path = "by-username")
    List<User> findByUsernameContainingIgnoreCase(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    @NonNull
    Page<User> findAllByActiveIsTrue(@NonNull Pageable pageable);
}

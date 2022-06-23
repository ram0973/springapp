package com.me.springapp.repository;

import com.me.springapp.model.ModelState;
import com.me.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @RestResource(rel = "by-email", path = "by-email")
    // HQL query example
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findById(int id);

    Optional<User> findByIdAndState(int id, ModelState state);

    Boolean existsByEmailIgnoreCase(String email);

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    Page<User> findAllByState(@NonNull Pageable pageable, ModelState state);
}

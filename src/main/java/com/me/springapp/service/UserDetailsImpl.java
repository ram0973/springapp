package com.me.springapp.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.me.springapp.exceptions.NoSuchUserException;
import com.me.springapp.model.User;
import com.me.springapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    UserRepository userRepository;

    public UserDetailsImpl() {
    }

    public UserDetailsImpl(int id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.toString())
            .toList());

        return new UserDetailsImpl(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO check JWT token?
        return true;
    }

    @Override
    public boolean isEnabled() {
        User user = userRepository.findById(this.id).orElseThrow(NoSuchUserException::new);
        return user.isActive();
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}

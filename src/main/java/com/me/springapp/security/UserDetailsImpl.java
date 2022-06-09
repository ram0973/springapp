package com.me.springapp.security;

import com.me.springapp.model.ModelState;
import com.me.springapp.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserDetailsImpl implements UserDetails {

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Getter
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getState().equals(ModelState.EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getState().equals(ModelState.LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //TODO password expired
    }

    @Override
    public boolean isEnabled() {
        return user.getState().equals(ModelState.ENABLED);
    }
}
package com.me.springapp.security.service;

import com.me.springapp.model.User;
import com.me.springapp.security.UserDetailsImpl;
import com.me.springapp.repository.UserRepository;
import com.me.springapp.security.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with email: " + email));
        return new UserDetailsImpl(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with email: " + email));
        return new UserDetailsImpl(user);
    }
}

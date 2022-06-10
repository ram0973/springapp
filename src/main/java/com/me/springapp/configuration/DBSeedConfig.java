package com.me.springapp.configuration;

import com.me.springapp.model.*;
import com.me.springapp.repository.ArticleRepository;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DBSeedConfig {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   ArticleRepository articleRepository) {
        return args -> {
            log.info("Preloading " + userRepository.save(
                new User(ModelState.ENABLED, LocalDateTime.now(), "bilbo@baggins.com",
                    passwordEncoder.encode("123"), null, Set.of(Role.ROLE_USER))));
            log.info("Preloading " + userRepository.save(
                new User(ModelState.ENABLED, LocalDateTime.now(), "frodo@baggins.com",
                    passwordEncoder.encode("123"), null,
                    Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR))));
            User gendalf = new User(ModelState.ENABLED, LocalDateTime.now(), "gendalf@white.com",
                passwordEncoder.encode("123"), null,
                Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR, Role.ROLE_ADMIN));
            log.info("Preloading " + userRepository.save(gendalf));

            for (int i = 0; i < 12; i++) {
                log.info("Preloading " + articleRepository.save(
                    // TODO: remove base constructor on migrations implement
                    new Article(ModelState.ENABLED, LocalDateTime.now(), String.format("Article №%d", i),
                        "Excerpt", "Content", null,  gendalf, null)));
            }
        };
    }
}

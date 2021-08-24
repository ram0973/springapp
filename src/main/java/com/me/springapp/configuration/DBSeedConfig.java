package com.me.springapp.configuration;

import com.me.springapp.model.Article;
import com.me.springapp.model.Role;
import com.me.springapp.model.User;
import com.me.springapp.repository.ArticleRepository;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DBSeedConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   ArticleRepository articleRepository) {
        return args -> {
            log.info("Preloading " + userRepository.save(
                new User("bilbo", "bilbo@baggins.com", passwordEncoder.encode("666666"),
                    User.USER_ACTIVE, LocalDateTime.now(), null,
                    Set.of(Role.ROLE_USER))));
            log.info("Preloading " + userRepository.save(
                new User("frodo", "frodo@baggins.com", passwordEncoder.encode("666666"),
                    User.USER_ACTIVE, LocalDateTime.now(), null,
                    Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR))));
            User gendalf = new User("gendalf", "gendalf@white.com",
                passwordEncoder.encode("666666"), User.USER_ACTIVE, LocalDateTime.now(),
                null, Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR, Role.ROLE_ADMIN));
            log.info("Preloading " + userRepository.save(gendalf));

            for (int i = 0; i < 12; i++) {
                log.info("Preloading " + articleRepository.save(
                    new Article(String.format("Article №%d", i), "Excerpt", "Content", null,
                        true, LocalDateTime.now(), gendalf, null)));
            }
        };
    }
}

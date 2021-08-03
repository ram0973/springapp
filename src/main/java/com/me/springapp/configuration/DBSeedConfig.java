package com.me.springapp.configuration;

import com.me.springapp.model.Article;
import com.me.springapp.model.Role;
import com.me.springapp.model.RoleEnum;
import com.me.springapp.model.User;
import com.me.springapp.repository.ArticleRepository;
import com.me.springapp.repository.RoleRepository;
import com.me.springapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor @Slf4j
public class DBSeedConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   ArticleRepository articleRepository) {
        return args -> {
            log.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_ADMIN)));
            log.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_MODERATOR)));
            log.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_USER)));

            log.info("Preloading " + userRepository.save(
                new User(0, "bilbo", "bilbo@baggins.com", passwordEncoder.encode("666666"),
                    User.USER_ACTIVE, LocalDateTime.now(),
                    Collections.singleton(roleRepository.findByName(RoleEnum.ROLE_USER).orElseThrow()))));
            log.info("Preloading " + userRepository.save(
                new User(1, "frodo", "frodo@baggins.com", passwordEncoder.encode("666666"),
                    User.USER_ACTIVE, LocalDateTime.now(),
                    Collections.singleton(roleRepository.findByName(RoleEnum.ROLE_MODERATOR).orElseThrow()))));
            User gendalf = new User(2, "gendalf", "gendalf@white.com",
                passwordEncoder.encode("666666"),
                User.USER_ACTIVE, LocalDateTime.now(),
                Collections.singleton(roleRepository.findByName(RoleEnum.ROLE_ADMIN).orElseThrow()));
            log.info("Preloading " + userRepository.save(gendalf));

            log.info("Preloading " + articleRepository.save(
                    new Article(0, "1 article", "Excerpt", "Content", null, true, gendalf, null)));
            log.info("Preloading " + articleRepository.save(
                    new Article(1, "2 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(2, "3 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(3, "4 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(4, "5 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(5, "6 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(6, "7 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(7, "8 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(8, "9 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(9, "10 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(10, "11 article", "Excerpt", "Content", true, gendalf)));
            log.info("Preloading " + articleRepository.save(
                    new Article(11, "12 article", "Excerpt", "Content", true, gendalf)));
        };
    }
}

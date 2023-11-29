package com.me.springapp.configuration;

import com.me.springapp.model.*;
import com.me.springapp.repository.ArticleRepository;
import com.me.springapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Slf4j
public class DBSeedConfig {
    private final PasswordEncoder passwordEncoder;

    public DBSeedConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, ArticleRepository articleRepository) {
        return args -> {
            log.info("Preloading Users");

            User bilbo = new User();
            bilbo.setState(UserState.ENABLED);
            bilbo.setDateCreated(LocalDateTime.now());
            bilbo.setEmail("bilbo@baggins.com");
            bilbo.setPassword(passwordEncoder.encode("123123"));
            bilbo.setArticles(null);
            bilbo.setRoles(Set.of(Role.ROLE_USER));
            userRepository.save(bilbo);

            User frodo = new User();
            frodo.setState(UserState.ENABLED);
            frodo.setDateCreated(LocalDateTime.now());
            frodo.setEmail("frodo@baggins.com");
            frodo.setPassword(passwordEncoder.encode("123123"));
            frodo.setArticles(null);
            frodo.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR));
            userRepository.save(frodo);

            User gendalf = new User();
            gendalf.setState(UserState.ENABLED);
            gendalf.setDateCreated(LocalDateTime.now());
            gendalf.setEmail("gendalf@white.com");
            gendalf.setPassword(passwordEncoder.encode("123123"));
            gendalf.setArticles(null);
            gendalf.setRoles(Set.of(Role.ROLE_USER, Role.ROLE_MODERATOR, Role.ROLE_ADMIN));
            userRepository.save(gendalf);

            log.info("Preloading Articles");
            ThreadLocalRandom random = ThreadLocalRandom.current();
            for (int i = 1; i < 40; i++) {
                Article article = new Article();
                article.setTitle(String.format("Article №%d", i));
                article.setExcerpt("Excerpt");
                article.setContent("Content");
                article.setImage("/images/1.jpg");
                article.setUser(gendalf);
                article.setState(ArticleState.ENABLED);
                article.setDateCreated(LocalDateTime.now().minusDays(random.nextInt(40)));
                articleRepository.save(article);
            }
        };
    }
}

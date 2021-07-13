package com.me.springapp.configuration;

import com.me.springapp.model.Article;
import com.me.springapp.model.Role;
import com.me.springapp.model.RoleEnum;
import com.me.springapp.model.User;
import com.me.springapp.repository.ArticleRepository;
import com.me.springapp.repository.RoleRepository;
import com.me.springapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DBSeedConfig {
    private static final Logger logger = LoggerFactory.getLogger(DBSeedConfig.class);

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBSeedConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   ArticleRepository articleRepository) {
        return args -> {
            logger.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_ADMIN)));
            logger.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_MODERATOR)));
            logger.info("Preloading " + roleRepository.save(new Role(RoleEnum.ROLE_USER)));

            User bilbo = new User("bilbo", "bilbo@baggins.com", passwordEncoder.encode("123456"),
                    User.USER_ACTIVE);
            logger.info("Preloading " + userRepository.save(bilbo));
            logger.info("Preloading " + userRepository.save(
                    new User("frodo", "frodo@baggins.com", passwordEncoder.encode("123456"),
                            User.USER_ACTIVE)));
            logger.info("Preloading " + userRepository.save(
                    new User("gendalf", "gendalf@white.com", passwordEncoder.encode("123456"),
                            User.USER_ACTIVE)));

            logger.info("Preloading " + articleRepository.save(
                    new Article("1 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("2 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("3 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("4 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("5 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("6 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("7 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("8 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("9 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("10 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("11 article", "Excerpt", "Content", true, bilbo)));
            logger.info("Preloading " + articleRepository.save(
                    new Article("12 article", "Excerpt", "Content", true, bilbo)));
        };
    }
}

package com.me.springapp.configuration;

import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.User;
import com.me.springapp.security.service.UserDetailsServiceImpl;
import com.me.springapp.security.userdetails.UserDetailsImpl;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

// https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
@Configuration
//@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    static final String PROJECT_LANGUAGE = "ru"; //TODO move to ini
    static final String[] ALLOWED_ORIGINS = {"http://localhost:8080"}; //TODO move to ini

    // resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.forLanguageTag(PROJECT_LANGUAGE));
        return sessionLocaleResolver;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


//    @Bean
//    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder());
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
//                                                       PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService);
//        authenticationProvider.setPasswordEncoder(passwordEncoder);
//        return new ProviderManager(authenticationProvider);
//    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(ALLOWED_ORIGINS));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Bean
    protected SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/blog/**").permitAll()
                    .requestMatchers("/", "/api/auth/login", "/api/auth/token").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    .anyRequest().permitAll()
                //.anyRequest().authenticated()
            )
            // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html
            // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-integration-mobile
            // check paths
            .csrf(AbstractHttpConfigurer::disable // TODO remove after testing
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )

            .rememberMe(rememberMe -> rememberMe
                .userDetailsService(userDetailsService)
                .key("1234567890") // TODO Change key
                .tokenRepository(persistentTokenRepository())
            )

            .formLogin(AbstractHttpConfigurer::disable)
            // https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                .logoutUrl("/api/auth/logout").permitAll()
                .logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .passwordManagement(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                    .sessionConcurrency(sessionConcurrency -> sessionConcurrency.maximumSessions(5)
                        //.expiredUrl("/login?expired")
                    )
            );

        //http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

package com.me.springapp.configuration;

import com.me.springapp.exceptions.NoSuchEntityException;
import com.me.springapp.model.User;
import com.me.springapp.security.service.AuthenticationProviderService;
import com.me.springapp.security.service.UserDetailsServiceImpl;
import com.me.springapp.security.userdetails.UserDetailsImpl;
import com.me.springapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.RedisIndexedHttpSessionConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.w3c.dom.css.CSSMediaRule;

import java.util.Locale;

// https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    static final String PROJECT_LANGUAGE = "ru"; //TODO move to ini
    static final String[] ALLOWED_ORIGINS = {"http://localhost:8080"}; //TODO move to ini

    //@Bean(name = "mvcHandlerMappingIntrospector")
    //public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
    //    return new HandlerMappingIntrospector();
    //}

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

    @Bean
    protected SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/", "/api/auth/login").permitAll()
                    //.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                    //.anyRequest().permitAll()
                    .anyRequest().authenticated()
            )
            // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html
            // https://docs.spring.io/spring-security/reference/servlet/exploits/csrf.html#csrf-integration-mobile
            // check paths
            .csrf(AbstractHttpConfigurer::disable // TODO remove after testing
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            //.formLogin(AbstractHttpConfigurer::disable)
            // https://docs.spring.io/spring-security/reference/servlet/authentication/logout.html
            .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                .loginPage("/login")
                .usernameParameter("email")
            )
            .httpBasic((basic) -> basic.securityContextRepository(new HttpSessionSecurityContextRepository()))
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            //.rememberMe(rememberMe -> rememberMe
            //    .useSecureCookie(true)
            //    .rememberMeServices()
            //)
            //.passwordManagement(AbstractHttpConfigurer::disable)
            //.httpBasic(AbstractHttpConfigurer::disable)

            // https://docs.spring.io/spring-security/reference/servlet/authentication/rememberme.html
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .sessionConcurrency(sessionConcurrency -> {} // sessionConcurrency.maximumSessions(5)
                        //.expiredUrl("/login?expired")
                    )
            );

        //http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

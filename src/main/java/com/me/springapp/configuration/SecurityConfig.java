package com.me.springapp.configuration;

import com.me.springapp.security.filter.JwtAuthenticationFilter;
import com.me.springapp.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private final JwtAuthenticationFilter jwtFilter;

    static final String PROJECT_LANGUAGE = "ru";
    static final String[] ALLOWED_ORIGINS = {"http://localhost:8080"};

    // resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
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

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

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

    //protected void configure(@NotNull AuthenticationManagerBuilder auth) {
    //    auth.authenticationProvider(authenticationProvider);
    //}
//    @Bean
//    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        builder.userDetailsService(builder.getDefaultUserDetailsService());
//        return builder.build();
//    }
    @Bean
    public AuthenticationManager authenticationManager(
        UserDetailsService userDetailsService,
        PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    protected SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {

//        http
//        http.csrf()
//            //.ignoringAntMatchers("/api/auth/login")
//            .disable(); // JWT
//        http.formLogin()
//            //.usernameParameter("email")
//            .disable();
//        http.httpBasic().disable();
//        http.logout().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //.loginPage("/")
        //.changePasswordPage("/update-password")
        http.authorizeHttpRequests(authorize -> authorize
                //.requestMatchers("/blog/**").permitAll()
                .anyRequest().permitAll()
                //.anyRequest().authenticated()
            )
            .formLogin(AbstractHttpConfigurer::disable)
            .passwordManagement(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
            //.cors().disable();
            //.mvcMatchers("/", "/api/auth/login", "/api/auth/token").permitAll()
            //.mvcMatchers( "/swagger-ui/**", "/v3/api-docs/**").permitAll()
        // authenticated();

        //http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

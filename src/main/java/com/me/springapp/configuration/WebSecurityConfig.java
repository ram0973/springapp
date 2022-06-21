package com.me.springapp.configuration;

import com.me.springapp.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalAuthentication
@EnableWebSecurity
// provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize, it also supports JSR-250
@EnableGlobalMethodSecurity(prePostEnabled = true) // securedEnabled = true, // jsr250Enabled = true,
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    private final JwtAuthenticationFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
            //.ignoringAntMatchers("/api/auth/login")
            .disable(); // JWT
        http.formLogin()
            //.usernameParameter("email")
            .disable();
        http.logout().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
            .mvcMatchers("/", "/error", "/api/test/**", "/api/auth/login", "/api/auth/token", "/swagger-ui/**")
            .permitAll()
            .anyRequest().permitAll();
        http.addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(new JwtOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        //http.exceptionHandling((exceptions) -> exceptions
        //    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
        //    .accessDeniedHandler(new BearerTokenAccessDeniedHandler()));
    }
}

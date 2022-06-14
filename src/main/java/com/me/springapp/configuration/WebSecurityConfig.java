package com.me.springapp.configuration;

import com.me.springapp.security.filter.JwtOncePerRequestFilter;
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
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf()
            .ignoringAntMatchers("/api/auth/login")
            .disable(); // JWT
        http.formLogin().usernameParameter("email").disable();
        http.logout().disable();
        http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
            .mvcMatchers("/", "/error", "/api/auth/**", "/api/test/**").permitAll()
            .anyRequest().authenticated();
        http.addFilterBefore(new JwtOncePerRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}

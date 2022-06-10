package com.me.springapp.configuration;

import com.me.springapp.security.filter.CustomAuthenticationFilter;
import com.me.springapp.security.jwt.AuthEntryPointJwt;
import com.me.springapp.security.service.AuthenticationProviderService;
import com.me.springapp.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalAuthentication
@EnableWebSecurity
// provides AOP security on methods. It enables @PreAuthorize, @PostAuthorize, it also supports JSR-250
@EnableGlobalMethodSecurity(prePostEnabled = true) // securedEnabled = true, // jsr250Enabled = true,
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    private AuthenticationProviderService authenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthenticationProviderService());
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable(); // JWT
        http.formLogin().usernameParameter("email");//.disable();
        //http.logout().disable();
        //http.httpBasic().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
            .mvcMatchers("/", "/error", "/api/auth/*", "/api/test/*").permitAll()
            .anyRequest().permitAll();
        http.addFilter(new CustomAuthenticationFilter());
    }
}

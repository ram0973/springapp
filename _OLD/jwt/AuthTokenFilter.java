package com.me.springapp.security.jwt;

import com.me.springapp.security.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Slf4j
public class AuthTokenFilter {//extends OncePerRequestFilter {

//    @Autowired
//    private JwtUtils jwtUtils;
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsServiceImpl;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//        throws ServletException, IOException {
//        final String AUTHORIZATION_HEADER_NAME = "Authorization";
//        final String JWT_BEARER_PREFIX = "Bearer ";
//
//        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
//        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(JWT_BEARER_PREFIX)) {
//            throw new InvalidJWT();
//        }
//        String jwt = authorizationHeader.substring(JWT_BEARER_PREFIX.length());
//        if (!jwtUtils.validateJwtToken(jwt)) {
//            throw new InvalidJWT();
//        }
//        String email = jwtUtils.getUserNameFromJwtToken(jwt);
//        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//            userDetails, null, userDetails.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
//    }
}

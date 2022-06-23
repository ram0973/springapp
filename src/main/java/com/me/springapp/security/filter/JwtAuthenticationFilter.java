package com.me.springapp.security.filter;

import com.me.springapp.security.model.JwtAuthentication;
import com.me.springapp.security.provider.JwtProvider;
import com.me.springapp.security.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {
        final Optional<String> token = this.getTokenFromRequest((HttpServletRequest) request);
        if (token.isPresent() && jwtProvider.validateAccessToken(token.get())) {
            final Claims claims = jwtProvider.getAccessClaims(token.get());
            final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        String JWT_PREFIX = "BEARER ";
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWT_PREFIX)) {
            return Optional.of(bearer.substring(JWT_PREFIX.length()));
        }
        return Optional.empty();
    }

}

package com.jsr_dev.medical_api.infra.security;

import com.jsr_dev.medical_api.domain.user.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    public SecurityFilter(
            TokenService tokenService,
            AuthenticationService authenticationService
    ) {
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (doesNotContainBearerToken(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = extractTokenValue(authHeader);
        String email = tokenService.extractSubject(jwtToken);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails foundUser = authenticationService.loadUserByUsername(email);

            if (tokenService.isValid(jwtToken, foundUser)) {
                updateContext(foundUser, request);
            }

            filterChain.doFilter(request, response);
        }
    }

    private String extractTokenValue(String header) {
        return header.substring("Bearer ".length());
    }

    private boolean doesNotContainBearerToken(String header) {
        return header == null || !header.startsWith("Bearer ");
    }

    private void updateContext(UserDetails foundUser, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                foundUser,
                null,
                foundUser.getAuthorities()
        );

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
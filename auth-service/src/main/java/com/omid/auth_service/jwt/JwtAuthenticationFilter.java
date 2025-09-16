package com.omid.auth_service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.omid.auth_service.authority.Authority;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtHandler jwtHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        doBefore(request, response);
        filterChain.doFilter(request, response);
    }

    private void doBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith("Bearer ")) {
            return;
        }

        String finalToken = token.substring("Bearer".length()).trim();

        DecodedJWT verifiedToken = jwtHandler.verifyToken(finalToken);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(verifiedToken.getSubject(), null, verifiedToken.getClaim("authorities").asList(Authority.class))
        );

    }
}

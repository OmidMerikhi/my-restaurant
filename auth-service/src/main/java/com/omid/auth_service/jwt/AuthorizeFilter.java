package com.omid.auth_service.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class AuthorizeFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        doBefore(request, response);
        filterChain.doFilter(request, response);
    }

    private void doBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getRequestURI().equals("/api/auth")) {
            AntPathMatcher matcher = new AntPathMatcher();
            String urlPath = request.getRequestURI();
            String methodeType = "_" + request.getMethod().toLowerCase() + "_";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            var authorities = auth.getAuthorities().stream().toList();
            AtomicBoolean ok = new AtomicBoolean(false);
            authorities.forEach(a-> {
                String mainAuthority = a.getAuthority().substring(a.getAuthority().indexOf("/api"));
                if (matcher.match(mainAuthority,urlPath) && a.getAuthority().contains(methodeType)) {
                    ok.set(true);
                }
            });

            if (ok.get()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            else {
                throw new ServletException("not accessable!");
            }

        }
    }
}

package com.omid.orderservice.security;

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


@Component
public class AuthorizeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            doBefore(request, response);
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            ex.printStackTrace(); // لاگ در کنسول
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"" + ex.getMessage() + "\"}");
        }
    }

    private void doBefore(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AntPathMatcher matcher = new AntPathMatcher();
        String urlPath = request.getRequestURI();
        String methodType = "_" + request.getMethod().toLowerCase() + "_";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new ServletException("❌ Authentication is null. Probably JWT not validated.");
        }

        System.out.println("🔹 URL: " + urlPath);
        System.out.println("🔹 Method: " + methodType);
        System.out.println("🔹 Principal: " + auth.getName());
        System.out.println("🔹 Authorities:");
        auth.getAuthorities().forEach(a -> System.out.println("   → " + a.getAuthority()));

        boolean ok = auth.getAuthorities().stream().anyMatch(a -> {
            String authority = a.getAuthority();
            if (authority.contains("/api")) {
                String mainAuthority = authority.substring(authority.indexOf("/api"));
                return matcher.match(mainAuthority, urlPath) && authority.contains(methodType);
            }
            return false;
        });

        if (!ok) {
            throw new ServletException("User not authorized for this endpoint");
        }
    }
}


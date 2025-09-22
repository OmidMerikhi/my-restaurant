//package com.omid.api_gateway.security;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//@ConfigurationProperties(prefix = "app.security")
//@Setter
//@Getter
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//    private String issuer;
//    private String secretKey;
//    @Override
//    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
//        doBefore(request, response);
//        filterChain.doFilter(request, response);
//    }
//
//    private void doBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String baseToken = request.getHeader("Authorization");
//
//        if (baseToken == null || !baseToken.startsWith("Bearer ")) {
//            response.setStatus(401);
//        }
//
//        String token = baseToken.substring(7);
//
//        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
//
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null , decodedJWT.getClaim("authorities").asList(Authority.class))
//        );
//
//    }
//}

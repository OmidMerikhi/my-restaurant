package com.omid.orderservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlacklistAwareJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final TokenBlacklistService blacklistService;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        String jti = jwt.getId();

        if (jti != null && blacklistService.isBlacklisted(jti)) {
            throw new JwtException("Token is blacklisted");
        }

        List<String> roles = jwt.getClaimAsStringList("authorities");

        List<GrantedAuthority> authorities =
                roles == null
                        ? List.of()
                        : roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new JwtAuthenticationToken(jwt, authorities);
    }
}

//@Component
//@RequiredArgsConstructor
//public class BlacklistAwareJwtAuthenticationConverter
//        implements Converter<Jwt, AbstractAuthenticationToken> {
//
//    private final TokenBlacklistService blacklistService;
//
//
////    @Override
////    public AbstractAuthenticationToken convert(Jwt jwt) {
////        System.out.println("🔹 JWT Claims: " + jwt.getClaims());
////        // گرفتن jti از توکن
////        String jti = jwt.getId(); // یا jwt.getClaimAsString("jti")
////
////        // چک لیست سیاه
////        if (jti != null && blacklistService.isBlacklisted(jti)) {
////            throw new JwtException("Token is blacklisted");
////        }
////
////        // گرفتن نقش‌ها
////        List<String> roles = jwt.getClaimAsStringList("authorities");
////
////        List<GrantedAuthority> authorities = roles == null
////                ? List.of()
////                : roles.stream()
////                .map(SimpleGrantedAuthority::new)
////                .collect(Collectors.toList());
////
////        // ساخت Authentication
////        return new UsernamePasswordAuthenticationToken(
////                jwt.getSubject(),
////                jwt,
////                authorities
////        );
////    }
//
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        System.out.println("🔥 Converter called");
//        System.out.println("JWT subject = " + jwt.getSubject());
//        System.out.println("JWT claims = " + jwt.getClaims());
//
//        // بررسی blacklisting
//        String jti = jwt.getId();
//        if (jti != null && blacklistService.isBlacklisted(jti)) {
//            throw new JwtException("Token is blacklisted");
//        }
//
//        // گرفتن نقش‌ها
//        List<String> roles = jwt.getClaimAsStringList("authorities");
//        List<GrantedAuthority> authorities = roles == null
//                ? List.of()
//                : roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        return new UsernamePasswordAuthenticationToken(jwt.getSubject(), jwt, authorities);
//    }
//}

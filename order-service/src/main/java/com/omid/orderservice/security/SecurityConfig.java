package com.omid.orderservice.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthorizeFilter authorizeFilter;
    private final JwtAutheticationFilter jwtAutheticationFilter;
//    private final DebugHeaderFilter debugHeaderFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.oauth2ResourceServer(o ->
                o.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );
        security.addFilterAfter(
                jwtAutheticationFilter,
                ExceptionTranslationFilter.class
        );
        security.addFilterAfter(authorizeFilter, JwtAutheticationFilter.class);
        security.authorizeHttpRequests(m-> {
            m.anyRequest().authenticated();

        });
        return security.build();
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("authorities");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity security,
//                                                   BlacklistAwareJwtAuthenticationConverter converter) throws Exception {
//        security.csrf(AbstractHttpConfigurer::disable);
//
//        // ✅ درست‌ترین نقطه برای Debug قبل از JWT
//        security.addFilterBefore(debugHeaderFilter, SecurityContextHolderFilter.class);
//
//        security.oauth2ResourceServer(o ->
//                o.jwt(jwt -> jwt.jwtAuthenticationConverter(converter))
//        );
//
//        security.addFilterAfter(authorizeFilter, ExceptionTranslationFilter.class);
//
//        security.authorizeHttpRequests(m -> m.anyRequest().authenticated());
//
//        return security.build();
//    }

//    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
//        converter.setAuthoritiesClaimName("authorities");
//        converter.setAuthorityPrefix("");
//
//        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
//        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
//        return jwtConverter;
//    }
}

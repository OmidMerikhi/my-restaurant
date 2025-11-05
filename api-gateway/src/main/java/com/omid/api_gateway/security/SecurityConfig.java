package com.omid.api_gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity security) throws Exception {
        security.csrf(ServerHttpSecurity.CsrfSpec::disable);

        security.authorizeExchange(m -> {
            m.pathMatchers("/api/auth/refresh", "/api/auth").permitAll();
            m.anyExchange().authenticated();
        });
//        security.oauth2ResourceServer(o -> {
//            o.jwt(Customizer.withDefaults());
//        });
        security.oauth2ResourceServer(o ->
                o.jwt(jwt -> jwt.jwtAuthenticationConverter(
                        new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter())
                )));
        return security.build();
    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return NimbusReactiveJwtDecoder.withSecretKey(
//                new javax.crypto.spec.SecretKeySpec("my-name-is-omid".getBytes(), "HmacSHA256")
//        ).build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
//        security.csrf(AbstractHttpConfigurer::disable);
//        security.oauth2ResourceServer(o -> o.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
//        security.addFilterAfter(authorizeFilter, ExceptionTranslationFilter.class);
//        security.authorizeHttpRequests(m -> {
//            m.anyRequest().authenticated();
//
//        });
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

    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();

        authoritiesConverter.setAuthoritiesClaimName("authorities");
        authoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }


}

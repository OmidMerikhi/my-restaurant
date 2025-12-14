package com.omid.auth_service;

//import com.omid.auth_service.jwt.AuthorizeFilter;
import com.nimbusds.jose.jwk.JWKSet;
import com.omid.auth_service.authority.Authority;
import com.omid.auth_service.authority.AuthorityService;
import com.omid.auth_service.jwt.AuthorizeFilter;
import com.omid.auth_service.jwt.JwtAuthenticationFilter;
import com.omid.auth_service.jwt.KeyManager;
import com.omid.auth_service.role.Role;
import com.omid.auth_service.role.RoleService;
import com.omid.auth_service.user.User;
import com.omid.auth_service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.converter.RsaKeyConverters;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import com.nimbusds.jose.jwk.*;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity
//@EnableConfigurationProperties(JwtHandler.class)

public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthorizeFilter authorizeFilter;

    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;
    private final KeyManager keyManager;
//    @Bean
//    public CommandLineRunner commandLineRunner(AuthorityService authorityService, RoleService roleService, UserService userService) {
//        return args -> {
//            var a1 = new Authority("my_restaurant__get_/api/foods");
//            var a2 = new Authority("my_restaurant__get_/api/foods/{id}");
//            authorityService.create(a1);
//            authorityService.create(a2);
//            //--------------------------------------
//            var r1 = new Role("user", Set.of(a1));
//            var r2 = new Role("admin", Set.of(a1, a2));
//            roleService.create(r1);
//            roleService.create(r2);
//            //----------------------------------------
//            var u1 = new User("omid", "123", Set.of(r1, r2));
//            var u2 = new User("reza", "321", Set.of(r1));
//            userService.create(u1);
//            userService.create(u2);
//        };
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
    @Bean
    public AuthenticationManager authenticationManager(UserService userService) {
        var provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        security.addFilterAfter(authenticationFilter, ExceptionTranslationFilter.class);
        security.addFilterAfter(authorizeFilter, JwtAuthenticationFilter.class);
        security.authorizeHttpRequests(m-> {
           m.requestMatchers("/api/auth/login", "/api/auth/oauth2/jwks").permitAll();
           m.requestMatchers("/api/auth/revoke").hasAuthority("my_restaurant__post_/api/foods").requestMatchers("/api/auth/revoke","/api/auth/load-black-list","/api/auth/key-rotation","/api/auth/load-all-keys").permitAll();
//           m.requestMatchers("/api/test/hi").hasAuthority("insert").requestMatchers("/api/test/hi").permitAll();
//           m.requestMatchers("/api/test/hello").hasAuthority("select").requestMatchers("/api/test/hello").permitAll();
           m.anyRequest().authenticated();
        });
        return security.build();
    }

    @Bean
    public RSAKey rsaKey() {
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("my-name-is-omid")
                .build();
    }

    @Bean
    public JWKSet jwkSet(KeyManager keyManager) {
        return new JWKSet(new ArrayList<>(keyManager.getAllKeys().values()));
    }




}

package com.omid.auth_service;

import com.omid.auth_service.authority.Authority;
import com.omid.auth_service.authority.AuthorityService;
import com.omid.auth_service.jwt.AuthorizeFilter;
import com.omid.auth_service.jwt.JwtAuthenticationFilter;
import com.omid.auth_service.jwt.JwtHandler;
import com.omid.auth_service.role.Role;
import com.omid.auth_service.role.RoleService;
import com.omid.auth_service.user.User;
import com.omid.auth_service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity
//@EnableConfigurationProperties(JwtHandler.class)

public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    private final AuthorizeFilter authorizeFilter;
//    @Bean
//    public CommandLineRunner commandLineRunner(AuthorityService authorityService, RoleService roleService, UserService userService) {
//        return args -> {
//            var a1 = new Authority("select");
//            var a2 = new Authority("insert");
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
           m.requestMatchers("/api/auth").permitAll();
           m.requestMatchers("/api/test/hi").hasAuthority("insert").requestMatchers("/api/test/hi").permitAll();
           m.requestMatchers("/api/test/hello").hasAuthority("select").requestMatchers("/api/test/hello").permitAll();
           m.anyRequest().authenticated();
        });
        return security.build();
    }


}

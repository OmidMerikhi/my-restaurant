package com.omid.auth_service.authentication;

import com.omid.auth_service.jwt.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;

    @PostMapping
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtHandler.generateToken(authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
    }
}

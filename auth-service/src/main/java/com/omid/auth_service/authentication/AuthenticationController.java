package com.omid.auth_service.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import com.omid.auth_service.jwt.JwtHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;
    private final JWKSet jwkSet;

    @PostMapping
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtHandler.generateToken(authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return jwkSet.toJSONObject();
    }
}

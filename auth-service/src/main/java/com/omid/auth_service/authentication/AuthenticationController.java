package com.omid.auth_service.authentication;

import com.omid.auth_service.jwt.JwtHandler;
import com.omid.auth_service.jwt.KeyService;
import com.omid.auth_service.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;
    private final RefreshTokenService refreshTokenService;
    private final KeyService keyService;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtHandler.generateToken(authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
//        String refreshToken = refreshTokenService.createRefreshToken();

    }

    @PostMapping("/revoke")
    public void revoke(@RequestParam("token") String token) throws Exception {
        refreshTokenService.revokeToken(token);
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return keyService.jwkSet().toJSONObject();
    }

    @GetMapping("/load-black-list")
    public List<String> loadBlackList() {
        return refreshTokenService.loadBlackList();
    }

    @PostMapping("/key-rotation")
    public void rotation() throws Exception {
        keyService.rotate();
    }
}

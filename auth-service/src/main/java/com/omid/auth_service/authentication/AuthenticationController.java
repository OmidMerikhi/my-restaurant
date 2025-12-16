package com.omid.auth_service.authentication;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.omid.auth_service.jwt.JwtHandler;
//import com.omid.auth_service.jwt.KeyManager;
import com.omid.auth_service.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtHandler jwtHandler;
    private final JWKSet jwkSet;
    private final RefreshTokenService refreshTokenService;
//    private final KeyManager keyManager;

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return jwtHandler.generateToken(authentication.getName(),
                authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new));
//        String refreshToken = refreshTokenService.createRefreshToken();

    }

    @PostMapping("/revoke")
    public void revoke(@RequestParam("token") String token) {
        refreshTokenService.revokeToken(token);
    }

    @GetMapping("/oauth2/jwks")
    public Map<String, Object> keys() {
        return jwkSet.toJSONObject();
    }

    @GetMapping("/load-black-list")
    public List<String> loadBlackList() {
        return refreshTokenService.loadBlackList();
    }

//    @PostMapping("/key-rotation")
//    public void keRotation() {
//        try {
//            keyManager.rotateKeys();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @GetMapping("/load-all-keys")
//    public Map<String, RSAKey> loadAllKeys() {
//        return keyManager.getAllKeys();
//    }

//    @PostMapping("/refresh")
//    public ResponseEntity<?> refresh(@RequestParam("refresh-token") String refreshToken) {
//        String oldRefreshToken = refreshToken;
//
//
//        // 3️⃣ تولید Refresh Token جدید (چرخش)
//        String newRefreshToken = refreshTokenService.createRefreshToken();
//
//        // 4️⃣ ساخت Access Token جدید
//        String newAccessToken = jwtHandler.generateToken(username, new String[]{"ROLE_USER"});
//
//        // 5️⃣ پاسخ نهایی
//        return ResponseEntity.ok(Map.of(
//                "accessToken", newAccessToken,
//                "refreshToken", newRefreshToken
//        ));
//    }
}

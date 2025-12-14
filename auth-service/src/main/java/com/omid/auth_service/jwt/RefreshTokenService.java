package com.omid.auth_service.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
//    private final RedisTemplate<String, String> redisTemplate;
    private final StringRedisTemplate redisTemplate;
    private final JwtHandler jwtHandler;

    private final long refreshTokenValidity = 7 * 24 * 60 * 60; // 7 روز بر حسب ثانیه

//    public String createRefreshToken() {
//        String refreshToken = UUID.randomUUID().toString();
//        redisTemplate.opsForValue().set(refreshToken, "refresh-token", refreshTokenValidity, TimeUnit.SECONDS);
//
//        // در Redis ذخیره کن
////        redisTemplate.opsForValue().set(refreshToken, username, refreshTokenValidity, TimeUnit.SECONDS);
//
//        return refreshToken;
//    }

//    public String getUsername(String refreshToken) {
//        return redisTemplate.opsForValue().get(refreshToken);
//    }

//    public boolean isValid(String token) {
//        String storedToken = redisTemplate.opsForValue().get(token);
//        return storedToken != null;
//    }

//    public void revoke(String refreshToken) {
//        redisTemplate.delete(refreshToken);
//    }

    public boolean isBlacklisted(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(jti));
    }

    public void revokeToken(String token) {
        DecodedJWT decodedToken = jwtHandler.verifyToken(token);
        redisTemplate.opsForValue().set(decodedToken.getId(), "black-token", 31536000, TimeUnit.SECONDS);
    }

//    public ResponseEntity<?> rotationToken(String refreshToken, long expirationInSeconds) {
//
//        redisTemplate.opsForValue().set(refreshToken, "blacklisted", expirationInSeconds, TimeUnit.SECONDS);
//        String newRefreshToken = createRefreshToken();
//        String username = getUsername(jti);
//        String newAccessToken = jwtHandler.generateToken(username, new String[]{"ROLE_USER"});
//        return ResponseEntity.ok(Map.of(
//                "accessToken", newAccessToken,
//                "refreshToken", newRefreshToken
//        ));
//    }




    // revocation token
    //fix rotation token
    //add redis black list validation to jwt authentication class
    //add redis black list validation to resource server other services

}

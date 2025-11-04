package com.omid.auth_service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    private final long refreshTokenValidity = 7 * 24 * 60 * 60; // 7 روز بر حسب ثانیه

    public String createToken(String username) {
        String refreshToken = UUID.randomUUID().toString();

        // در Redis ذخیره کن
        redisTemplate.opsForValue().set(refreshToken, username, refreshTokenValidity, TimeUnit.SECONDS);

        return refreshToken;
    }

    public String getUsername(String refreshToken) {
        return redisTemplate.opsForValue().get(refreshToken);
    }

    public boolean isValid(String token) {
        String storedToken = redisTemplate.opsForValue().get(token);
        return storedToken != null;
    }

    public void revoke(String refreshToken) {
        redisTemplate.delete(refreshToken);
    }

}

package org.saas.auth.service.impl;

import org.saas.auth.dto.RefreshTokenRequest;
import org.saas.auth.dto.RefreshTokenResponse;
import org.saas.auth.security.JwtTokenProvider;
import org.saas.auth.service.RefreshTokenService;
import org.saas.core.dto.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {


    private final JwtTokenProvider jwtTokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${security.jwt.refresh-token}")
    private long refreshTokenExpireSeconds;

    // refreshToken <-> AuthUser eşleşmesi
    private final Map<String, AuthUser> refreshTokenStore = new ConcurrentHashMap<>();

    public RefreshTokenServiceImpl(JwtTokenProvider jwtTokenProvider, RedisTemplate<String, String> redisTemplate) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public RefreshTokenResponse generateTokens(AuthUser user) {
      //  String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = generateRandomRefreshToken();
        String key = String.valueOf(user.id());


        // refresh token'ı sakla
        redisTemplate.opsForValue().set(refreshToken, key, Duration.ofSeconds(refreshTokenExpireSeconds));


        return new RefreshTokenResponse(refreshToken);
    }

    @Override
    public RefreshTokenResponse refreshAccessToken(RefreshTokenRequest request) {
        String userId = redisTemplate.opsForValue().get(request.refreshToken());

        if (userId == null) {
            throw new RuntimeException("Refresh token geçersiz veya süresi dolmuş.");
        }
        String accessToken = jwtTokenProvider.generateTokenRefresh(userId);
        return new RefreshTokenResponse(accessToken);

    }

    @Override
    public void logout(String refreshToken) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(refreshToken))) {
            redisTemplate.delete(refreshToken);
        }
        //redisTemplate.delete(refreshToken);
    }


    private String generateRandomRefreshToken() {
        return java.util.UUID.randomUUID().toString();
    }


}

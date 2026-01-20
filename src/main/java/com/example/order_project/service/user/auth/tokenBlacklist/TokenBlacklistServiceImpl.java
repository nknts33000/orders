package com.example.order_project.service.user.auth.tokenBlacklist;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private static final String PREFIX = "jwt:blacklist:";

    private final RedisTemplate<String, String> redisTemplate;

    public TokenBlacklistServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void blacklist(String token, Instant expiresAt) {
        long ttlSeconds = Duration.between(Instant.now(), expiresAt).getSeconds();

        if (ttlSeconds > 0) {
            redisTemplate.opsForValue()
                    .set(PREFIX + token, "blacklisted", ttlSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(
                redisTemplate.hasKey(PREFIX + token)
        );
    }
}


package com.example.order_project.service.user.auth.tokenBlacklist;

import java.time.Instant;

public interface TokenBlacklistService {
    void blacklist(String token, Instant expiresAt);
    boolean isBlacklisted(String token);
}

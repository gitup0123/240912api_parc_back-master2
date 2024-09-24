package com.parc.api;

import com.parc.api.service.TokenBlacklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TokenBlacklistServiceTest {
    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    private TokenBlacklistService tokenBlacklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        tokenBlacklistService = new TokenBlacklistService(redisTemplate);
    }
    @Test
    void testBlacklistToken() {
        String token = "testToken";
        long expirationTime = 1000L;
        tokenBlacklistService.blacklistToken(token, expirationTime);


        verify(valueOperations).set(
                eq("blacklisted_token:testToken"),
                eq("true"),
                eq(expirationTime),
                eq(TimeUnit.MILLISECONDS)
        );
}
    @Test
    void testIsTokenBlacklisted_whenTokenIsBlacklisted() {
        String token = "blacklistedToken";
        when(redisTemplate.hasKey("blacklisted_token:" + token)).thenReturn(true);
        boolean result = tokenBlacklistService.isTokenBlacklisted(token);

        assertTrue(result);
        verify(redisTemplate).hasKey("blacklisted_token:" + token);
    }
    @Test
    void testIsTokenBlacklisted_whenTokenIsNotBlacklisted() {
        String token = "validToken";
        when(redisTemplate.hasKey("blacklisted_token:" + token)).thenReturn(false);
        boolean result = tokenBlacklistService.isTokenBlacklisted(token);

        assertFalse(result);
        verify(redisTemplate).hasKey("blacklisted_token:" + token);
    }
}

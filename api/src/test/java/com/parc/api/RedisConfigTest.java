package com.parc.api;

import com.parc.api.configuration.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedisConfigTest {
    @Test
    void testRedisConnectionFactory() {
        RedisConfig redisConfig = new RedisConfig();
        LettuceConnectionFactory connectionFactory = redisConfig.redisConnectionFactory();

        assertNotNull(connectionFactory);
        assertTrue(connectionFactory instanceof LettuceConnectionFactory);
    }
    @Test
    void testRedisTemplate() {
        RedisConfig redisConfig = new RedisConfig();
        LettuceConnectionFactory connectionFactory = redisConfig.redisConnectionFactory();
        StringRedisTemplate template = redisConfig.redisTemplate(connectionFactory);

        assertNotNull(template);
        assertTrue(template.getConnectionFactory() instanceof LettuceConnectionFactory);
    }

}

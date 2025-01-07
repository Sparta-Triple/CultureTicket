package com.culture_ticket.client.performance.infrastructure.config;

import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${spring.data.redis.host}")
    private String redisServer;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.username}")
    private String redisUsername;

    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean
    public Jackson2JsonRedisSerializer<PerformanceResponseDto> jackson2JsonRedisSerializer() {
        return new Jackson2JsonRedisSerializer<>(PerformanceResponseDto.class);
    }

    // RedisTemplate을 사용하여 직렬화/역직렬화 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // 키 직렬화: 문자열로 저장
        template.setKeySerializer(new StringRedisSerializer());

        // 값 직렬화: PerformanceResponseDto를 JSON으로 직렬화
        template.setValueSerializer(jackson2JsonRedisSerializer());

        // 해시 키/값 직렬화
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }


    @Bean
    public CacheManager cacheManager() {
        // 기본 Redis 캐시 설정
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))  // 캐시 TTL 설정 (5분)
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory())
                .cacheDefaults(cacheConfig)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisServer);
        configuration.setPort(redisPort);
        configuration.setUsername(redisUsername);
        configuration.setPassword(redisPassword);
        return new LettuceConnectionFactory(configuration);
    }
}

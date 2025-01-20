package com.culture_ticket.client.performance.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ScheduledTaskExecutor {

    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 0 0 * * SUN") // 매주 일요일 자정에 실행
    public void resetPerformanceRedisCache() {
        String pattern = "performance*";
        Set<String> keys = redisTemplate.keys(pattern);
        if (!keys.isEmpty()) {
            redisTemplate.delete(keys);
            System.out.println("Deleted keys: " + keys);
        } else {
            System.out.println("No keys found matching pattern: " + pattern);
        }
    }
}

package com.culture_ticket.client.performance.infrastructure.fallback;

import com.culture_ticket.client.performance.infrastructure.client.QueueClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueFallbackFactory implements FallbackFactory<QueueClient> {
    @Override
    public QueueClient create(Throwable cause) {
        log.error("Error in CompanyProductClient: {}", cause.getMessage(), cause);
        return new QueueFallback();
    }
}

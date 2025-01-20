package com.culture_ticket.client.performance.infrastructure.client;

import com.culture_ticket.client.performance.application.dto.feignclient.FeignClientResponseDataDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueRequestDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueResponseDto;
import com.culture_ticket.client.performance.infrastructure.config.FeignClientConfig;
import com.culture_ticket.client.performance.infrastructure.fallback.QueueFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "queue-service",
    configuration = FeignClientConfig.class,
    fallbackFactory = QueueFallbackFactory.class
)
public interface QueueClient {

    @PostMapping("/api/v1/queues/token")
    FeignClientResponseDataDto<WaitingQueueResponseDto> checkWaiting(WaitingQueueRequestDto requestDto);

}

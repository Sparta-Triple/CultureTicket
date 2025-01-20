package com.culture_ticket.client.performance.infrastructure.client;

import com.culture_ticket.client.performance.application.dto.feignclient.FeignClientResponseDataDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueRequestDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "queue-service")
public interface QueueClient {

    @PostMapping("/api/v1/queues/token")
    FeignClientResponseDataDto<WaitingQueueResponseDto> checkWaiting(WaitingQueueRequestDto requestDto);

}

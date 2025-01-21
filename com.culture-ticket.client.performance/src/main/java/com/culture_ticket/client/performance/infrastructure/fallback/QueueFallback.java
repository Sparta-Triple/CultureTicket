package com.culture_ticket.client.performance.infrastructure.fallback;

import com.culture_ticket.client.performance.application.dto.feignclient.FeignClientResponseDataDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueRequestDto;
import com.culture_ticket.client.performance.application.dto.feignclient.WaitingQueueResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.infrastructure.client.QueueClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueFallback implements QueueClient {

    @Override
    public FeignClientResponseDataDto<WaitingQueueResponseDto> checkWaiting(
        WaitingQueueRequestDto requestDto) {
        log.error("Fallback triggered for getCompany: requestDto= {}", requestDto);
        throw new CustomException(ErrorType.QUEUE_NOT_FOUND);
    }
}

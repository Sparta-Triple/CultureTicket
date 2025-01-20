package com.culture_ticket.client.queue.application.dto.request;

import lombok.Data;

@Data
public class WaitingQueueRequestDto {
    private String sessionId;
    private String token;
}

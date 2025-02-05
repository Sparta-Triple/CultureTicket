package com.culture_ticket.client.performance.application.dto.feignclient;

import lombok.Data;

@Data
public class WaitingQueueRequestDto {
    private String sessionId;
    private String token;

    public WaitingQueueRequestDto(String sessionId, String token) {
        this.sessionId = sessionId;
        this.token = token;
    }
}

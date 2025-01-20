package com.culture_ticket.client.performance.application.dto.feignclient;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WaitingQueueResponseDto {
    private String sessionId;
    private String token;
    private boolean isActive;
    private WaitingInfo waitingInfo;

    @Builder
    private WaitingQueueResponseDto(String sessionId, String token, boolean isActive,
        WaitingInfo waitingInfo) {
        this.sessionId = sessionId;
        this.token = token;
        this.isActive = isActive;
        this.waitingInfo = waitingInfo;
    }

    public record WaitingInfo(
        long waitingNumber,
        long waitTimeInSeconds
    ) {
    }
}

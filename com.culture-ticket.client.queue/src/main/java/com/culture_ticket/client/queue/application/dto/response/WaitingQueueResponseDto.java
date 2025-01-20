package com.culture_ticket.client.queue.application.dto.response;

import static com.culture_ticket.client.queue.domain.medel.WaitingQueue.WaitingQueueStatus.ACTIVE;

import com.culture_ticket.client.queue.domain.medel.WaitingQueue;
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

    public static WaitingQueueResponseDto of(String sessionId, String token, boolean isActive,
        WaitingInfo waitingInfo) {
        return builder()
            .sessionId(sessionId)
            .token(token)
            .isActive(isActive)
            .waitingInfo(waitingInfo)
            .build();
    }

    public static WaitingQueueResponseDto from(WaitingQueue queue) {
        return builder()
            .sessionId(queue.getSessionId())
            .token(queue.getToken())
            .isActive(queue.getStatus() == ACTIVE)
            .waitingInfo(queue.getWaitingNum() != null ? new WaitingInfo(queue.getWaitingNum(), queue.getWaitTimeInSeconds()) : null)
            .build();
    }

    public record WaitingInfo(
        long waitingNumber,
        long waitTimeInSeconds
    ) {
    }
}

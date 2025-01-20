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
    private Long userId;
    private String token;
    private boolean isActive;
    private WaitingInfo waitingInfo;

    @Builder
    private WaitingQueueResponseDto(Long userId, String token, boolean isActive,
        WaitingInfo waitingInfo) {
        this.userId = userId;
        this.token = token;
        this.isActive = isActive;
        this.waitingInfo = waitingInfo;
    }

    public static WaitingQueueResponseDto of(Long userId, String token, boolean isActive,
        WaitingInfo waitingInfo) {
        return builder()
            .userId(userId)
            .token(token)
            .isActive(isActive)
            .waitingInfo(waitingInfo)
            .build();
    }

    public static WaitingQueueResponseDto from(WaitingQueue queue) {
        return builder()
            .userId(queue.getUserId())
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

package com.culture_ticket.client.queue.domain.medel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "waiting_queue")
public class WaitingQueueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID waitingQueueId;

    private Long userId;

    private String token;

    @Enumerated(EnumType.STRING)
    private WaitingQueue.WaitingQueueStatus status; // 대기 / 활성 / 만료

    private LocalDateTime requestTime; // 토큰 요청 시각

    private LocalDateTime activeTime; // 토큰 활성화 시각

    public static WaitingQueueEntity toEntity(WaitingQueue queue) {
        return builder()
                .waitingQueueId(queue.getWaitingQueueId() != null ? queue.getWaitingQueueId() : null)
                .userId(queue.getUserId())
                .token(queue.getToken())
                .status(queue.getStatus())
                .requestTime(queue.getRequestTime())
                .activeTime(queue.getActiveTime() != null ? queue.getActiveTime() : null)
                .build();
    }

    public WaitingQueue toDomain() {
        return WaitingQueue.builder()
                .waitingQueueId(waitingQueueId)
                .userId(userId)
                .token(token)
                .status(status)
                .requestTime(requestTime)
                .activeTime(activeTime)
                .build();
    }

}

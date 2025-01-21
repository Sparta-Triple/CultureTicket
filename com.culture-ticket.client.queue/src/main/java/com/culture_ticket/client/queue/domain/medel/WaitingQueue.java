package com.culture_ticket.client.queue.domain.medel;

import static com.culture_ticket.client.queue.domain.medel.WaitingQueueConstants.MAX_ACTIVE_USER;
import static java.time.LocalDateTime.now;

import com.culture_ticket.client.queue.common.CustomException;
import com.culture_ticket.client.queue.common.ErrorType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class WaitingQueue {
    private UUID waitingQueueId;

    private String sessionId;

    private String token;

    private WaitingQueueStatus status; // 대기 / 활성 / 만료

    private LocalDateTime requestTime; // 토큰 요청 시각

    private LocalDateTime activeTime; // 토큰 활성화 시각

    private Long waitingNum;

    private Long waitTimeInSeconds;

    @Builder(toBuilder = true)
    private WaitingQueue(UUID waitingQueueId, String sessionId, String token, WaitingQueueStatus status,
        LocalDateTime requestTime, LocalDateTime activeTime, Long waitingNum, Long waitTimeInSeconds) {
        this.waitingQueueId = waitingQueueId;
        this.sessionId = sessionId;
        this.token = token;
        this.status = status;
        this.requestTime = requestTime;
        this.activeTime = activeTime;
        this.waitingNum = waitingNum;
        this.waitTimeInSeconds = waitTimeInSeconds;
    }

    public void addWaitingInfo(long waitingNum, long waitTimeInSeconds) {
        this.waitingNum = waitingNum;
        this.waitTimeInSeconds = waitTimeInSeconds;
    }


    public static long calculateActiveCnt(long activeTokenCnt) {

        return MAX_ACTIVE_USER - activeTokenCnt;
    }

    public enum WaitingQueueStatus {

        WAIT, // 대기 중
        ACTIVE, // 활성화
        EXPIRED // 만료
    }

    public static WaitingQueue toDomain(long availableActiveTokenCnt, String sessionId, String token) {

        if (availableActiveTokenCnt > 0) return WaitingQueue.toActiveDomain(sessionId, token);

        return WaitingQueue.toWaitingDomain(sessionId, token);
    }

    public static WaitingQueue toActiveDomain(String sessionId, String token) {
        return WaitingQueue.builder()
                .sessionId(sessionId)
                .token(token)
                .status(WaitingQueueStatus.ACTIVE)
                .requestTime(now())
                .activeTime(now())
                .build();
    }

    public static WaitingQueue toWaitingDomain(String sessionId, String token) {
        return WaitingQueue.builder()
                .sessionId(sessionId)
                .token(token)
                .status(WaitingQueueStatus.WAIT)
                .requestTime(now())
                .build();
    }


    public void isActive() {
        if (status == WaitingQueueStatus.ACTIVE) {
            throw new CustomException(ErrorType.ALREADY_TOKEN_IS_ACTIVE);
        }
    }


    public void expireOver10min() {
        if (activeTime.isAfter(LocalDateTime.now().plusMinutes(10))) { //10분뒤에 만료
            throw new CustomException(ErrorType.TOKEN_IS_NOT_YET);
        }
        expire();
    }

    public void expire() {
        status = WaitingQueueStatus.EXPIRED;
    }

    public void active() {
        if (status == WaitingQueueStatus.ACTIVE) {
            throw new CustomException(ErrorType.ALREADY_TOKEN_IS_ACTIVE);
        }
        status = WaitingQueueStatus.ACTIVE;
        activeTime = LocalDateTime.now();
    }


}

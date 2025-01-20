package com.culture_ticket.client.queue.application.service;

import com.culture_ticket.client.queue.application.dto.request.WaitingQueueRequestDto;
import com.culture_ticket.client.queue.application.dto.response.WaitingQueueResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingQueueFacade {

    private final WaitingQueueService waitingQueueService;

    /**
     * 토큰을 발급하고, 대기열 정보를 확인하는 유즈케이스를 실행한다.
     *
     * @param userId userId 정보
     * @param request token 정보
     * @return WaitingQueueResponse 대기열 정보를 반환한다.
     */
    public WaitingQueueResponseDto checkWaiting(Long userId, WaitingQueueRequestDto request) {
        // 유저 정보 조회
//        User user = userService.getUser(command.userId()); -> TODO: feign client

        return waitingQueueService.checkWaiting(userId, request.getToken());
    }

    /**
     * 대기열에 있는 토큰을 순차적으로 active 시키는 유즈케이스를 실행한다.
     */
    public void active() {
        waitingQueueService.activeTokens();
    }
}

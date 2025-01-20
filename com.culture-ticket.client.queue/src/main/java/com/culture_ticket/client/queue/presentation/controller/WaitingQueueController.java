package com.culture_ticket.client.queue.presentation.controller;

import com.culture_ticket.client.queue.application.dto.request.WaitingQueueRequestDto;
import com.culture_ticket.client.queue.application.dto.response.WaitingQueueResponseDto;
import com.culture_ticket.client.queue.application.service.WaitingQueueFacade;
import com.culture_ticket.client.queue.common.ResponseDataDto;
import com.culture_ticket.client.queue.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/queues")
public class WaitingQueueController {

    private final WaitingQueueFacade waitingQueueFacade;

    /**
     * 대기열 활성여부 조회
     *
     * @param request userId 정보
     * @return 토큰 정보와 대기열 정보를 반환한다.(토큰 요청값이 없으면 새로 발급하여 응답 반환, isActive 반환값에 따라 페이지 진입 가능 여부를 판단합니다)
     */
    @PostMapping("/token")
    public ResponseDataDto<WaitingQueueResponseDto> checkWaiting(
        @RequestBody WaitingQueueRequestDto request) {
        return new ResponseDataDto<>(ResponseStatus.QUEUE_GET_SUCCESS,
            waitingQueueFacade.checkWaiting(request));
    }
}

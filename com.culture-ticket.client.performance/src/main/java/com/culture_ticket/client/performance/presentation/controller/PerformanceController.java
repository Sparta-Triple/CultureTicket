package com.culture_ticket.client.performance.presentation.controller;


import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.service.PerformanceService;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    // 공연 생성
    @PostMapping
    public ResponseMessageDto createPerformance(
            @RequestBody PerformanceCreateRequestDto performanceCreateRequestDto
    ) {
        performanceService.createPerformance(performanceCreateRequestDto);
        return new ResponseMessageDto(ResponseStatus.CREATE_PERFORMANCE_SUCCESS);
    }

    // 공연 단일 조회 (performanceId)

    // 공연 목록 조회

    // 공연 검색 (title)

    // 공연 상태 수정

    // 공연 내용 수정

    // 공연 삭제
}

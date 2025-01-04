package com.culture_ticket.client.performance.presentation.controller;


import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceStatusRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.culture_ticket.client.performance.application.service.PerformanceService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @GetMapping("/{performanceId}")
    public ResponseDataDto<PerformanceResponseDto> getPerformance(@PathVariable UUID performanceId){
        PerformanceResponseDto performanceResponseDto = performanceService.getPerformance(performanceId);
        return new ResponseDataDto<>(ResponseStatus.GET_PERFORMANCE_SUCCESS, performanceResponseDto);
    }


    // 공연 목록 조회

    // 공연 검색 (title)

    // 공연 상태 수정
    @PatchMapping("/{performanceId}")
    public ResponseMessageDto updatePerformanceStatus(
            @PathVariable UUID performanceId,
            @RequestBody UpdatePerformanceStatusRequestDto updatePerformanceStatusRequestDto
    ){
        performanceService.updatePerformanceStatus(performanceId, updatePerformanceStatusRequestDto);
        return new ResponseMessageDto(ResponseStatus.UPDATE_PERFORMANCE_STATUS_SUCCESS);
    }

    // 공연 수정
    @PutMapping("/{performanceId}")
    public ResponseMessageDto updatePerformance(
            @PathVariable UUID performanceId,
            @RequestBody UpdatePerformanceRequestDto updatePerformanceRequestDto
    ){
        performanceService.updatePerformance(performanceId, updatePerformanceRequestDto);
        return new ResponseMessageDto(ResponseStatus.UPDATE_PERFORMANCE);
    }

    // 공연 삭제
    @DeleteMapping("/{performanceId}")
    public ResponseMessageDto deletePerformance(@PathVariable UUID performanceId){
        performanceService.deletePerformance(performanceId);
        return new ResponseMessageDto(ResponseStatus.DELETE_PERFORMANCE_SUCCESS);
    }
}

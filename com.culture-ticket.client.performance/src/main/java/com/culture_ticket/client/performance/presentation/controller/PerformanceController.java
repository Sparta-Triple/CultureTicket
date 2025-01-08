package com.culture_ticket.client.performance.presentation.controller;


import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceDomainCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdatePerformanceStatusRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.PerformanceResponseDto;
import com.culture_ticket.client.performance.application.service.PerformanceService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import com.culture_ticket.client.performance.domain.service.PerformanceDomainService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
public class PerformanceController {

  private final PerformanceService performanceService;
  private final PerformanceDomainService performanceDomainService;

  // 공연 도메인 생성
  @PostMapping("/domain")
  public ResponseMessageDto createPerformanceDomain(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestBody PerformanceDomainCreateRequestDto performanceDomainCreateRequestDto
  ) {
    performanceDomainService.createPerformanceDomain(username, role, performanceDomainCreateRequestDto);
    return new ResponseMessageDto(ResponseStatus.CREATE_PERFORMANCE_DOMAIN_SUCCESS);
  }

  // 공연 생성
  @PostMapping
  public ResponseMessageDto createPerformance(
      @RequestBody PerformanceCreateRequestDto performanceCreateRequestDto,
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role
  ) {
    performanceService.createPerformance(username, role, performanceCreateRequestDto);
    return new ResponseMessageDto(ResponseStatus.CREATE_PERFORMANCE_SUCCESS);
  }

  // 공연 단일 조회 (performanceId)
  @GetMapping("/info/{performanceId}")
  public ResponseDataDto<PerformanceResponseDto> getPerformance(@PathVariable UUID performanceId) {
    PerformanceResponseDto performanceResponseDto = performanceService.getPerformance(
        performanceId);
    return new ResponseDataDto<>(ResponseStatus.GET_PERFORMANCE_SUCCESS, performanceResponseDto);
  }

  // 공연 목록 조회 & 검색 (title)
  @GetMapping("/info")
  public ResponseDataDto<Page<PerformanceResponseDto>> getPerformances(
      @RequestParam(value = "condition", required = false) String condition,
      @RequestParam(value = "keyword", required = false) String keyword,
      @PageableDefault Pageable pageable
  ) {
    Page<PerformanceResponseDto> performances = performanceService.getPerformances(condition,
        keyword, pageable);
    return new ResponseDataDto<>(ResponseStatus.GET_PERFORMANCE_SUCCESS, performances);
  }

  // 공연 상태 수정
  @PatchMapping("/{performanceId}")
  public ResponseMessageDto updatePerformanceStatus(
      @PathVariable UUID performanceId,
      @RequestBody UpdatePerformanceStatusRequestDto updatePerformanceStatusRequestDto,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestHeader(value = "X-User-Name") String username
  ) {
    performanceService.updatePerformanceStatus(role, username, performanceId,
        updatePerformanceStatusRequestDto);
    return new ResponseMessageDto(ResponseStatus.UPDATE_PERFORMANCE_STATUS_SUCCESS);
  }

  // 공연 수정
  @PutMapping("/{performanceId}")
  public ResponseMessageDto updatePerformance(
      @PathVariable UUID performanceId,
      @RequestBody UpdatePerformanceRequestDto updatePerformanceRequestDto,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestHeader(value = "X-User-Name") String username
  ) {
    performanceService.updatePerformance(role, username, performanceId,
        updatePerformanceRequestDto);
    return new ResponseMessageDto(ResponseStatus.UPDATE_PERFORMANCE);
  }

  // 공연 삭제
  @DeleteMapping("/{performanceId}")
  public ResponseMessageDto deletePerformance(
      @PathVariable UUID performanceId,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestHeader(value = "X-User-Name") String username
  ) {
    performanceService.deletePerformance(role, username, performanceId);
    return new ResponseMessageDto(ResponseStatus.DELETE_PERFORMANCE_SUCCESS);
  }
}

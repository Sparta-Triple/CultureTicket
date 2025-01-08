package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import com.culture_ticket.client.performance.application.service.TimeTableService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import com.culture_ticket.client.performance.domain.model.TimeTableStatus;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/timetables")
public class TimeTableController {

  private final TimeTableService timeTableService;

  // 생성
  @PostMapping
  public ResponseMessageDto createTimeTable(
      @RequestHeader(value = "X-User-Role") String role,
      @RequestHeader(value = "X-User-Name") String username,
      @RequestParam(value = "performanceId") UUID performanceId,
      @RequestBody TimeTableCreateRequestDto requestDto
  ) {
    timeTableService.createTimeTable(username, role, performanceId, requestDto);
    return new ResponseMessageDto(ResponseStatus.CREATE_TIME_TABLE_SUCCESS);
  }

  // 검색
  @GetMapping
  public ResponseDataDto<List<TimeTableSearchResponseDto>> searchTimeTables(
      TimeTableSearchRequestDto requestDto
  ) {
    List<TimeTableSearchResponseDto> responseDtos = timeTableService.searchTimeTables(requestDto);
    return new ResponseDataDto<>(ResponseStatus.SEARCH_TIME_TABLE_SUCCESS,responseDtos);
  }

  // 상태 수정
  @PutMapping("/{timeTableId}")
  public ResponseMessageDto updateTimeTableStatus(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @PathVariable UUID timeTableId,
      @RequestBody TimeTableStatus timeTableStatus
  ){
    timeTableService.updateTimeTableStatus(timeTableId, timeTableStatus, username, role);
    return new ResponseMessageDto(ResponseStatus.UPDATE_TIMETABLE_STATUS_SUCCESS);
  }

  // 삭제
  @DeleteMapping("/{timeTableId}")
  public ResponseMessageDto deleteTimeTable(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @PathVariable UUID timeTableId
  ){
    timeTableService.deleteTimeTable(timeTableId, username, role);
    return new ResponseMessageDto(ResponseStatus.DELETE_TIMETABLE_SUCCESS);
  }

  // 복구
  @PatchMapping("/{timeTableId}")
  public ResponseMessageDto restoreTimeTable(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @PathVariable UUID timeTableId
  ){
    timeTableService.restoreTimeTable(timeTableId, username, role);
    return new ResponseMessageDto(ResponseStatus.RESTORE_TIMETABLE_SUCCESS);
  }
}

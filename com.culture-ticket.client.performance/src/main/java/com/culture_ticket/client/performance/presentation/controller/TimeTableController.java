package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import com.culture_ticket.client.performance.application.service.TimeTableService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/timetables")
public class TimeTableController {

  private final TimeTableService timeTableService;

  @PostMapping
  public ResponseMessageDto createTimeTable(
      @RequestBody TimeTableCreateRequestDto requestDto,
      @RequestHeader(value = "X-User-Role") String role
  ) {
    timeTableService.createTimeTable(requestDto, role);
    return new ResponseMessageDto(ResponseStatus.CREATE_TIME_TABLE_SUCCESS);
  }

  @GetMapping
  public ResponseDataDto<List<TimeTableSearchResponseDto>> searchTimeTables(
      TimeTableSearchRequestDto requestDto,
      @RequestHeader(value = "X-User-Role") String role
  ) {
    List<TimeTableSearchResponseDto> responseDtos = timeTableService.searchTimeTables(requestDto, role);
    return new ResponseDataDto<>(ResponseStatus.SEARCH_TIME_TABLE_SUCCESS,responseDtos);
  }

}

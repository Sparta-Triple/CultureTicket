package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import java.util.List;

public interface TimeTableRepositoryCustom {

  List<TimeTableSearchResponseDto> searchTimeTable(TimeTableSearchRequestDto timeTableSearchRequestDto);

}

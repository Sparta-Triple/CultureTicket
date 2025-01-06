package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import com.culture_ticket.client.performance.common.util.RoleValidator;
import com.culture_ticket.client.performance.domain.model.TimeTable;
import com.culture_ticket.client.performance.domain.repository.TimeTableRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeTableService {

  private final TimeTableRepository timeTableRepository;

  public void createTimeTable(TimeTableCreateRequestDto requestDto, String role) {
    RoleValidator.validateIsAdmin(role);
    timeTableRepository.save(TimeTable.from(requestDto));
  }

  public List<TimeTableSearchResponseDto> searchTimeTables(TimeTableSearchRequestDto requestDto, String role) {
    return timeTableRepository.searchTimeTable(requestDto);
  }
}

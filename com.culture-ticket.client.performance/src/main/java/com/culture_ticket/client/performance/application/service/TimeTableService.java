package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.common.util.RoleValidator;
import com.culture_ticket.client.performance.domain.model.Performance;
import com.culture_ticket.client.performance.domain.model.TimeTable;
import com.culture_ticket.client.performance.domain.model.TimeTableStatus;
import com.culture_ticket.client.performance.domain.repository.PerformanceRepository;
import com.culture_ticket.client.performance.domain.repository.TimeTableRepository;
import com.culture_ticket.client.performance.presentation.controller.TimeTableInfoResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeTableService {

  private final TimeTableRepository timeTableRepository;
  private final PerformanceRepository performanceRepository;

  public List<UUID> createTimeTable(String username, String role, UUID performanceId, List<TimeTableCreateRequestDto> requestDtos) {
    RoleValidator.validateIsAdmin(role);
    Performance performance = performanceRepository.findById(performanceId).orElseThrow(
        () -> new CustomException(ErrorType.PERFORMANCE_NOT_FOUND)
    );
    List<TimeTable> timeTables = TimeTable.of(username, performance, requestDtos);
    timeTableRepository.saveAll(timeTables);
    return timeTables.stream().map(TimeTable::getId).toList();
  }

    public TimeTableInfoResponseDto getTimeTable(UUID timeTableId) {
      TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(() ->
          new CustomException(ErrorType.TIMETABLE_NOT_FOUND));

        return new TimeTableInfoResponseDto(timeTable);
    }

  public List<TimeTableSearchResponseDto> searchTimeTables(TimeTableSearchRequestDto requestDto) {
    return timeTableRepository.searchTimeTable(requestDto);
  }

  public void updateTimeTableStatus(UUID timeTableId, TimeTableStatus timeTableStatus, String username, String role) {
    RoleValidator.validateIsAdmin(role);
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    timeTable.updateStatus(timeTableStatus, username);
  }

  public void deleteTimeTable(UUID timeTableId, String username, String role) {
    RoleValidator.validateIsAdmin(role);
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    timeTable.deleted(username);
  }

  public void restoreTimeTable(UUID timeTableId, String username, String role) {
    RoleValidator.validateIsAdmin(role);
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    timeTable.restore(username);
  }
}

package com.culture_ticket.client.performance.domain.service;

import com.culture_ticket.client.performance.application.dto.requestDto.PerformanceDomainCreateRequestDto;
import com.culture_ticket.client.performance.application.service.PerformanceService;
import com.culture_ticket.client.performance.application.service.SeatService;
import com.culture_ticket.client.performance.application.service.TimeTableService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceDomainService {

  private final PerformanceService performanceService;
  private final TimeTableService timeTableService;
  private final SeatService seatService;

  public void createPerformanceDomain(String username, String role, PerformanceDomainCreateRequestDto performanceDomainCreateRequestDto){
    UUID performanceId = performanceService.createPerformance(username, role, performanceDomainCreateRequestDto.getPerformanceCreateRequestDto());
    UUID timeTableId = timeTableService.createTimeTable(username, role, performanceId, performanceDomainCreateRequestDto.getTimeTableCreateRequestDto());
    seatService.createSeats(username, role, timeTableId, performanceDomainCreateRequestDto.getSeatCreateRequestDtos());
  }
}

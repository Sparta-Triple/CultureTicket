package com.culture_ticket.client.performance.application.dto.requestDto;

import java.util.List;
import lombok.Getter;

@Getter
public class PerformanceDomainCreateRequestDto {

  private PerformanceCreateRequestDto performanceCreateRequestDto;
  private List<TimeTableCreateRequestDto> timeTableCreateRequestDtos;
  private List<SeatCreateRequestDto> seatCreateRequestDtos;

}

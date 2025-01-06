package com.culture_ticket.client.performance.application.dto.requestDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class TimeTableCreateRequestDto {
  private UUID performanceId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
}

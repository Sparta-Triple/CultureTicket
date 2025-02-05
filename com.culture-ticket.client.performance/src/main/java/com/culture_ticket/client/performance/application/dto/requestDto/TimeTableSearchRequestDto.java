package com.culture_ticket.client.performance.application.dto.requestDto;

import com.culture_ticket.client.performance.domain.model.TimeTableStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTableSearchRequestDto {

  private UUID performanceId;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private TimeTableStatus timeTableStatus;

}

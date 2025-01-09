package com.culture_ticket.client.performance.application.dto.responseDto;

import com.culture_ticket.client.performance.domain.model.TimeTableStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableSearchResponseDto {

  private LocalDate date;
  private LocalTime startTime;
  private LocalTime endTime;
  private TimeTableStatus timeTableStatus;
}

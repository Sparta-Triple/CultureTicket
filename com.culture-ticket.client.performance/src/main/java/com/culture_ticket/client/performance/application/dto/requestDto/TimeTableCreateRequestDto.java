package com.culture_ticket.client.performance.application.dto.requestDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class TimeTableCreateRequestDto {
  private LocalDate date;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime startTime;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
  private LocalTime endTime;
}

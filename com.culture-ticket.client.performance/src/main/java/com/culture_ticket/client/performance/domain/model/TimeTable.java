package com.culture_ticket.client.performance.domain.model;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "p_time_table")
@Builder(access = AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID performanceId;
  private LocalDate date;
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime startTime;
  @DateTimeFormat(pattern = "HH:mm")
  private LocalTime endTime;

  @Enumerated(EnumType.STRING)
  private TimeTableStatus timeTableStatus;

  public static TimeTable from(TimeTableCreateRequestDto requestDto){
    return builder()
        .performanceId(requestDto.getPerformanceId())
        .date(requestDto.getDate())
        .startTime(requestDto.getStartTime())
        .endTime(requestDto.getEndTime())
        .timeTableStatus(TimeTableStatus.AVAILABLE)
        .build();
  }

}

package com.culture_ticket.client.performance.domain.model;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableCreateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_time_table")
@Builder(access = AccessLevel.PROTECTED)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private LocalDate date;
  @Column(columnDefinition = "TIME(0)")
  private LocalTime startTime;
  @Column(columnDefinition = "TIME(0)")
  private LocalTime endTime;
  @Enumerated(EnumType.STRING)
  private TimeTableStatus timeTableStatus;
  @ManyToOne(fetch = FetchType.LAZY)
  private Performance performance;

  public static TimeTable of(String username, Performance performance, TimeTableCreateRequestDto requestDto){
    TimeTable timeTable = builder()
        .date(requestDto.getDate())
        .startTime(requestDto.getStartTime())
        .endTime(requestDto.getEndTime())
        .timeTableStatus(TimeTableStatus.AVAILABLE)
        .performance(performance)
        .build();
    timeTable.createdBy(username);
    return timeTable;
  }

  public void updateStatus(TimeTableStatus status, String username){
    this.timeTableStatus = status;
    updatedBy(username);
  }

  public void deleted(String username) {
    softDeletedBy(username);
  }

  public void restore(String username) {
    restoreBy(username);
  }
}

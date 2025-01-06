package com.culture_ticket.client.performance.domain.repository;

import com.culture_ticket.client.performance.application.dto.requestDto.TimeTableSearchRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.TimeTableSearchResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

import static com.culture_ticket.client.performance.domain.model.QTimeTable.timeTable;

@RequiredArgsConstructor
public class TimeTableRepositoryCustomImpl implements TimeTableRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<TimeTableSearchResponseDto> searchTimeTable(
      TimeTableSearchRequestDto requestDto) {
    return jpaQueryFactory
        .select(Projections.constructor(
            TimeTableSearchResponseDto.class,
            timeTable.date,
            timeTable.startTime,
            timeTable.endTime,
            timeTable.timeTableStatus
        ))
        .from(timeTable)
        .where(
            // 공연 아이디가 같은지 (필수, 없으면 모든 공연의 모든 타임 테이블 조회)
            requestDto.getPerformanceId() != null ? timeTable.performanceId.eq(requestDto.getPerformanceId()) : null,
            // 날짜가 같은지 (날짜 선택 시 해당 날짜만. 선택 안 할 시 해당 공연의 모든 타임 테이블)
            requestDto.getDate() != null ? timeTable.date.eq(requestDto.getDate()) : null,
            // 시작 시간 (좌석 관련 정보 연동 시 필요. 아직은 필요 x)
            requestDto.getStartTime() != null ? timeTable.startTime.eq(requestDto.getStartTime()) : null,
            // 종료 시간 (좌석 관련 정보 연동 시 필요. 아직은 필요 x)
            requestDto.getEndTime() != null ? timeTable.endTime.eq(requestDto.getEndTime()) : null,
            // 타임 테이블 상태
            requestDto.getTimeTableStatus() != null ? timeTable.timeTableStatus.eq(requestDto.getTimeTableStatus()) : null
        )
        .orderBy(
            timeTable.date.asc(),
            timeTable.startTime.asc()
        )
        .fetch();
  }
}

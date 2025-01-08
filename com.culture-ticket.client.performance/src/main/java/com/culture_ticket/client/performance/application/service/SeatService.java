package com.culture_ticket.client.performance.application.service;

import com.culture_ticket.client.performance.application.dto.requestDto.SeatCreateRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdateSeatPriceRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.SeatInfoResponseDto;
import com.culture_ticket.client.performance.common.CustomException;
import com.culture_ticket.client.performance.common.ErrorType;
import com.culture_ticket.client.performance.common.util.RoleValidator;
import com.culture_ticket.client.performance.domain.model.Seat;
import com.culture_ticket.client.performance.domain.model.SeatStatus;
import com.culture_ticket.client.performance.domain.model.TimeTable;
import com.culture_ticket.client.performance.domain.repository.PerformanceRepository;
import com.culture_ticket.client.performance.domain.repository.SeatRepository;
import com.culture_ticket.client.performance.domain.repository.TimeTableRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

  private final PerformanceRepository performanceRepository;
  private final TimeTableRepository timeTableRepository;
  private final SeatRepository seatRepository;

  public void createSeats(String username, String role, UUID timeTableId, List<SeatCreateRequestDto> requestDtos) {
    RoleValidator.validateIsAdmin(role);
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    for (SeatCreateRequestDto seatCreateRequestDto : requestDtos) {
      List<Seat> seats = Seat.of(timeTable, seatCreateRequestDto, username);
      seatRepository.saveAll(seats);
    }
  }

  public List<SeatInfoResponseDto> getSeats(UUID performanceId, UUID timeTableId) {
    performanceRepository.findById(performanceId).orElseThrow(
        () -> new CustomException(ErrorType.PERFORMANCE_NOT_FOUND)
    );
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    List<Seat> seats = seatRepository.findAllByTimeTable(timeTable);
    return seats.stream().map(SeatInfoResponseDto::new).toList();
  }

  public void updateSeatsStatus(String username, SeatStatus seatStatus, List<UUID> seatIds) {
    List<Seat> seats = seatRepository.findAllById(seatIds);
    for (Seat seat : seats) {
      seat.updateStatus(username, seatStatus);
    }
  }

  public void deleteSeats(List<UUID> seatIds, String username, String role) {
    RoleValidator.validateIsAdmin(role);
    List<Seat> seats = seatRepository.findAllById(seatIds);
    for (Seat seat : seats) {
      seat.deleted(username);
    }
  }

  public void restoreSeats(List<UUID> seatIds, String username, String role) {
    RoleValidator.validateIsAdmin(role);
    List<Seat> seats = seatRepository.findAllById(seatIds);
    for (Seat seat : seats) {
      seat.restore(username);
    }
  }

  public void updateSeatsPrice(String username, String role, UUID timeTableId, UpdateSeatPriceRequestDto requestDto) {
    RoleValidator.validateIsAdmin(role);
    TimeTable timeTable = timeTableRepository.findById(timeTableId).orElseThrow(
        () -> new CustomException(ErrorType.TIMETABLE_NOT_FOUND)
    );
    List<Seat> seats = seatRepository.findAllByTimeTableAndSeatClass(timeTable, requestDto.getSeatClass());
    for (Seat seat : seats) {
      seat.updatePrice(requestDto.getNewPrice(), username);
    }
  }
}

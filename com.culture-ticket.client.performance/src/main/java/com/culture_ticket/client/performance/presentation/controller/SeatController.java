package com.culture_ticket.client.performance.presentation.controller;

import com.culture_ticket.client.performance.application.dto.requestDto.CreateSeatRequestDto;
import com.culture_ticket.client.performance.application.dto.requestDto.UpdateSeatPriceRequestDto;
import com.culture_ticket.client.performance.application.dto.responseDto.SeatInfoResponseDto;
import com.culture_ticket.client.performance.application.service.SeatService;
import com.culture_ticket.client.performance.common.ResponseDataDto;
import com.culture_ticket.client.performance.common.ResponseMessageDto;
import com.culture_ticket.client.performance.common.ResponseStatus;
import com.culture_ticket.client.performance.domain.model.SeatStatus;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seats")
public class SeatController {

  private final SeatService seatService;

  // 목록 생성
  @PostMapping
  public ResponseMessageDto createSeats(
      @RequestHeader(value = "X-User-Role") String role,
      @RequestHeader(value = "X-User-Name") String username,
      @RequestParam(name = "timeTableId") UUID timeTableId,
      @RequestBody List<CreateSeatRequestDto> requestDtos
  ) {
    seatService.createSeats(timeTableId, requestDtos, username, role);
    return new ResponseMessageDto(ResponseStatus.CREATE_SEAT_SUCCESS);
  }

  // 목록 조회
  @GetMapping
  public ResponseDataDto<List<SeatInfoResponseDto>> getSeats(
      @RequestParam UUID performanceId,
      @RequestParam UUID timeTableId
  ) {
    List<SeatInfoResponseDto> responseDtos = seatService.getSeats(performanceId, timeTableId);
    return new ResponseDataDto<>(ResponseStatus.GET_SEAT_INFO_SUCCESS,responseDtos);
  }

  // 좌석 상태 변경
  @PatchMapping("/status/{seatStatus}")
  public ResponseMessageDto updateSeatsStatusAvailable(
      @RequestHeader(value = "X-User-Name") String username,
      @PathVariable SeatStatus seatStatus,
      @RequestBody List<UUID> seatIds
  ){
    seatService.updateSeatsStatus(username, seatStatus, seatIds);
    return new ResponseMessageDto(ResponseStatus.UPDATE_SEAT_STATUS_SUCCESS);
  }

  //좌석 가격 수정
  @PatchMapping("/price")
  public ResponseMessageDto updateSeatsPrice(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestParam(value = "timeTableId") UUID timeTableId,
      @RequestBody UpdateSeatPriceRequestDto requestDto
  ){
    seatService.updateSeatsPrice(username, role, timeTableId, requestDto);
    return new ResponseMessageDto(ResponseStatus.UPDATE_SEAT_PRICE_SUCCESS);
  }


  // 삭제
  @DeleteMapping
  public ResponseMessageDto deleteSeats(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestBody List<UUID> seatIds
  ){
    seatService.deleteSeats(seatIds, username, role);
    return new ResponseMessageDto(ResponseStatus.DELETE_SEAT_SUCCESS);
  }

  // 복구
  @PatchMapping
  public ResponseMessageDto restoreSeats(
      @RequestHeader(value = "X-User-Name") String username,
      @RequestHeader(value = "X-User-Role") String role,
      @RequestBody List<UUID> seatIds
  ){
    seatService.restoreSeats(seatIds, username, role);
    return new ResponseMessageDto(ResponseStatus.RESTORE_SEAT_SUCCESS);
  }

}

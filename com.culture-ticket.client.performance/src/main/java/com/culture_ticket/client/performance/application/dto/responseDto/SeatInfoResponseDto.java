package com.culture_ticket.client.performance.application.dto.responseDto;

import com.culture_ticket.client.performance.domain.model.Seat;
import com.culture_ticket.client.performance.domain.model.SeatStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SeatInfoResponseDto {

  private UUID seatId;
  private String seatClass;
  private Integer seatNumber;
  private Long seatPrice;
  private SeatStatus seatStatus;

  public SeatInfoResponseDto(Seat seat){
    this.seatId = seat.getId();
    this.seatClass = seat.getSeatClass();
    this.seatNumber = seat.getSeatNumber();
    this.seatPrice = seat.getSeatPrice();
    this.seatStatus = seat.getSeatStatus();
  }

}

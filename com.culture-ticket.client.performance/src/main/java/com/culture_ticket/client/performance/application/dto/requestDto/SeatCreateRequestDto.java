package com.culture_ticket.client.performance.application.dto.requestDto;

import lombok.Getter;

@Getter
public class SeatCreateRequestDto {

  private String seatClass;
  private Long price;
  private Integer count;

}

package com.culture_ticket.client.performance.application.dto.requestDto;

import lombok.Getter;

@Getter
public class UpdateSeatPriceRequestDto {

  private String seatClass;
  private Long newPrice;

}

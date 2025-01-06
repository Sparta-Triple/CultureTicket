package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SeatResponseDto {
    UUID timeTableId;
    String seatClass;
    Integer seatNumber;
    long price;

    @Builder
    private SeatResponseDto(UUID timeTableId, String seatClass, Integer seatNumber, long price) {
        this.timeTableId = timeTableId;
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.price = price;
    }

    public static SeatResponseDto from(SeatResponseDto seat) {
        return builder()
            .timeTableId(seat.getTimeTableId())
            .seatClass(seat.getSeatClass())
            .seatNumber(seat.getSeatNumber())
            .price(seat.getPrice())
            .build();
    }
}

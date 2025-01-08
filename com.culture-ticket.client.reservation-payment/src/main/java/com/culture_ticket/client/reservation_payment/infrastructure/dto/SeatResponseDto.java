package com.culture_ticket.client.reservation_payment.infrastructure.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SeatResponseDto {
    UUID seatId;
    UUID timeTableId;
    String seatClass;
    Integer seatNumber;
    long price;
    String seatStatus;

    @Builder
    public SeatResponseDto(UUID seatId, UUID timeTableId, String seatClass, Integer seatNumber, long price, String seatStatus) {
        this.seatId = seatId;
        this.timeTableId = timeTableId;
        this.seatClass = seatClass;
        this.seatNumber = seatNumber;
        this.price = price;
        this.seatStatus = seatStatus;
    }

    public static SeatResponseDto from(SeatResponseDto seat) {
        return builder()
            .seatId(seat.getSeatId())
            .timeTableId(seat.getTimeTableId())
            .seatClass(seat.getSeatClass())
            .seatNumber(seat.getSeatNumber())
            .price(seat.getPrice())
            .seatStatus(seat.getSeatStatus())
            .build();
    }
}

package com.culture_ticket.client.reservation_payment.application.dto.feignclient;

import java.util.UUID;
import lombok.Getter;

@Getter
public class SeatResponseDto {
    UUID seatId;
    UUID timeTableId;
    String seatClass;
    Integer seatNumber;
    Long seatPrice;
    String seatStatus;

//    @Builder
//    private SeatResponseDto(UUID seatId, UUID timeTableId, String seatClass, Integer seatNumber, long price, String seatStatus) {
//        this.seatId = seatId;
//        this.timeTableId = timeTableId;
//        this.seatClass = seatClass;
//        this.seatNumber = seatNumber;
//        this.seatPrice = price;
//        this.seatStatus = seatStatus;
//    }
//
//    public static SeatResponseDto from(SeatResponseDto seat) {
//        return builder()
//            .seatId(seat.getSeatId())
//            .timeTableId(seat.getTimeTable())
//            .seatClass(seat.getSeatClass())
//            .seatNumber(seat.getSeatNumber())
//            .price(seat.getSeatPrice())
//            .seatStatus(seat.getSeatStatus())
//            .build();
//    }
}

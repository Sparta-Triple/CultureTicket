package com.culture_ticket.client.reservation_payment.domain.model;

import lombok.Getter;

@Getter
public class Seat {
    String seatClass;
    Integer seatNumber;
    long Price;
    String seatStatus;
}

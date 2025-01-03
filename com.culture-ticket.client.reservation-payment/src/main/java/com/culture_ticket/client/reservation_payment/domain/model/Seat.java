package com.culture_ticket.client.reservation_payment.domain.model;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Seat {
    UUID timeTableId;
    String seatClass;
    Integer seatNumber;
    long Price;
}

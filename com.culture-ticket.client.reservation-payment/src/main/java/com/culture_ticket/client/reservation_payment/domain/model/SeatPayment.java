package com.culture_ticket.client.reservation_payment.domain.model;

import jakarta.persistence.*;

import java.util.UUID;
import lombok.Getter;

@Getter
@Entity
public class SeatPayment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private UUID seatId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;
}

package com.culture_ticket.client.reservation_payment.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "resevation-payment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private UUID ticketId;

    @Column(nullable = false)
    private UUID timeTableId;

    @Column(nullable = false)
    private ReservationStatus reservationStatus;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;
}

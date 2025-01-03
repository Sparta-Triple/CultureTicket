package com.culture_ticket.client.reservation_payment.domain.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class TicketPayment extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column
    private UUID ticketId;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment paymentId;
}

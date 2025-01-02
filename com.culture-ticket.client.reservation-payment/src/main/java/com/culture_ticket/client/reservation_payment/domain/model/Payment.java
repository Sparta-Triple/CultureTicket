package com.culture_ticket.client.reservation_payment.domain.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private UUID userId;

    private long totalPrice;

    @OneToMany(mappedBy = "ticket_payment_id")
    private List<TicketPayment> ticketPayments;
}

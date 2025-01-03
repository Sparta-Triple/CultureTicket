package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

}

package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Payment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByUserId(Long userId);
}

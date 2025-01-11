package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.SeatPayment;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatPaymentRepository extends JpaRepository<SeatPayment, UUID> {

    Optional<SeatPayment> findBySeatId(UUID seatId);
}

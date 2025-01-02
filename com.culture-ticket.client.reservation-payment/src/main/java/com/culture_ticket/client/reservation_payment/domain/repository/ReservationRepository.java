package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

}

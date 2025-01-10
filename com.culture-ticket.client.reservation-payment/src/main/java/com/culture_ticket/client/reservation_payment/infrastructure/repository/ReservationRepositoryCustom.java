package com.culture_ticket.client.reservation_payment.infrastructure.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReservationRepositoryCustom {

    Page<Reservation> findAllByUserIdAndDateRange(String userId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

    Page<Reservation> findAllByDateRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}

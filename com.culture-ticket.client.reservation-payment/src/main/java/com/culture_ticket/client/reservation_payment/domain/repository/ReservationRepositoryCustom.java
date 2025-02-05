package com.culture_ticket.client.reservation_payment.domain.repository;

import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ReservationRepositoryCustom {
    Page<Reservation> findAllByUserIdAndDateRange(String userId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    Page<Reservation> findAllByDateRange(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate); // 추가된 부분
}

package com.culture_ticket.client.reservation_payment.infrastructure.repository;

import com.culture_ticket.client.reservation_payment.domain.model.QReservation;
import com.culture_ticket.client.reservation_payment.domain.model.Reservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Reservation> findAllByUserIdAndDateRange(String userId, Pageable pageable,
        LocalDateTime startDate, LocalDateTime endDate) {
        QReservation reservation = QReservation.reservation;
        BooleanExpression userFilter = reservation.userId.eq(Long.valueOf(userId));
        BooleanExpression dateFilter = reservation.createdAt.between(startDate, endDate);
        List<Reservation> reservations = queryFactory
            .selectFrom(reservation)
            .where(dateFilter)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        long total = queryFactory
            .selectFrom(reservation)
            .where(userFilter.and(dateFilter))
            .fetch().size();
        return new PageImpl<>(reservations, pageable, total);
    }

    @Override
    public Page<Reservation> findAllByDateRange(Pageable pageable, LocalDateTime startDate,
        LocalDateTime endDate) {
        QReservation reservation = QReservation.reservation;
        List<Reservation> reservations = queryFactory
            .selectFrom(reservation)
            .where(reservation.createdAt.between(startDate, endDate))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
        long total = queryFactory
            .selectFrom(reservation)
            .where(reservation.createdAt.between(startDate, endDate))
            .fetch().size();
        return new PageImpl<>(reservations, pageable, total);
    }
}

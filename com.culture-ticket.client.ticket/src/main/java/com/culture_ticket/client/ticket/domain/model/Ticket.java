package com.culture_ticket.client.ticket.domain.model;

import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_ticket")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "performance_id", nullable = false)
    private UUID performanceId;

    @Column(name = "seat_id", nullable = false)
    private UUID seatId;

    @Column(name = "ticket_price", nullable = false)
    private Long ticketPrice;

    @Column(name = "reservation_id", nullable = false)
    private UUID reservationId;

    @Builder
    private Ticket(Long userId, UUID performanceId, UUID seatId, Long ticketPrice,
        UUID reservationId) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.seatId = seatId;
        this.ticketPrice = ticketPrice;
        this.reservationId = reservationId;
    }

    public static Ticket of(String userId, TicketRequestDto requestDto) {
        return builder()
            .userId(Long.parseLong(userId))
            .performanceId(requestDto.getPerformanceId())
            .seatId(requestDto.getSeatId())
            .ticketPrice(requestDto.getTicketPrice())
            .reservationId(requestDto.getReservationId())
            .build();
    }

    public void deleted(String username) {
        softDeletedBy(username);
    }

    public void created(String username) {
        createdBy(username);
    }
}

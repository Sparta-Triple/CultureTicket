package com.culture_ticket.client.ticket.domain.model;

import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "p_ticket")
public class Ticket extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "performance_id", nullable = false)
    private UUID performanceId;

    @Column(name = "seat_ids", nullable = false)
    private List<UUID> seatIds;

    @Column(name = "ticket_price", nullable = false)
    private Long ticketPrice;

    @Builder
    private Ticket(Long userId, UUID performanceId, List<UUID> seatIds, Long ticketPrice) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.seatIds = seatIds;
        this.ticketPrice = ticketPrice;
    }

    public static Ticket of(String userId, TicketRequestDto requestDto) {
        return builder()
            .userId(Long.parseLong(userId))
            .performanceId(requestDto.getPerformanceId())
            .seatIds(requestDto.getSeatIds())
            .ticketPrice(requestDto.getTicketPrice())
            .build();
    }

    public void deleted(String username) {
        softDeletedBy(username);
    }
}

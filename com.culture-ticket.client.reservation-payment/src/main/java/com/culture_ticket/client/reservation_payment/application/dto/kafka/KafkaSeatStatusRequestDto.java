package com.culture_ticket.client.reservation_payment.application.dto.kafka;

import com.culture_ticket.client.reservation_payment.domain.model.SeatStatus;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KafkaSeatStatusRequestDto {
    private String username;
    private SeatStatus seatStatus;
    private List<UUID> seatIds;

    @Builder
    private KafkaSeatStatusRequestDto(String username, SeatStatus seatStatus,
        List<UUID> seatIds) {
        this.username = username;
        this.seatStatus = seatStatus;
        this.seatIds = seatIds;
    }

    public static KafkaSeatStatusRequestDto of(String username, SeatStatus seatStatus,
        List<UUID> seatIds) {
        return builder()
            .username(username)
            .seatStatus(seatStatus)
            .seatIds(seatIds)
            .build();
    }
}

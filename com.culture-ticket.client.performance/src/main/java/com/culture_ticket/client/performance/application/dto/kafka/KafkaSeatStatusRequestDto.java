package com.culture_ticket.client.performance.application.dto.kafka;

import com.culture_ticket.client.performance.domain.model.SeatStatus;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KafkaSeatStatusRequestDto {
    private String username;
    private SeatStatus seatStatus;
    private List<UUID> seatIds;
}

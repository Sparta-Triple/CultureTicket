package com.culture_ticket.client.reservation_payment.application.dto.requestDto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class SeatSelectionRequestDto {

    @NotEmpty
    private List<UUID> seatIds;

}

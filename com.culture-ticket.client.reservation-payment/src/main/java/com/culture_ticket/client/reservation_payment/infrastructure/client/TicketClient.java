package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseMessageDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TicketRequestDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ticket-service")
public interface TicketClient {

    @PostMapping("/api/v1/tickets")
    FeignClientResponseMessageDto createTicket(
        @RequestHeader("X-User-Id") String userId,
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @RequestBody TicketRequestDto request);

    @DeleteMapping("/api/v1/tickets/reservation/{reservationId}")
    FeignClientResponseMessageDto deleteTicket(
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable UUID reservationId
    );
}

package com.culture_ticket.client.reservation_payment.infrastructure.client;

import com.culture_ticket.client.reservation_payment.infrastructure.dto.FeignClientResponseMessageDto;
import com.culture_ticket.client.reservation_payment.infrastructure.dto.TicketRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
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

}

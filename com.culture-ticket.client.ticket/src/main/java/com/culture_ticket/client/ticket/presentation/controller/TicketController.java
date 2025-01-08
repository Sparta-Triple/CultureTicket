package com.culture_ticket.client.ticket.presentation.controller;

import com.culture_ticket.client.ticket.application.dto.TicketRequestDto;
import com.culture_ticket.client.ticket.application.service.TicketService;
import com.culture_ticket.client.ticket.common.ResponseMessageDto;
import com.culture_ticket.client.ticket.common.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    /**
     * 티켓 생성
     *
     * @param username
     * @param role
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ResponseMessageDto> createTicket(
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @RequestBody TicketRequestDto request) {

        ticketService.createTicket(username, role, request);

        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.CREATE_TICKET_SUCCESS));
    }
}

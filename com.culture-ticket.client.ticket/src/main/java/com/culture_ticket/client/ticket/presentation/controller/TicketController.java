package com.culture_ticket.client.ticket.presentation.controller;

import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.response.TicketResponseDto;
import com.culture_ticket.client.ticket.application.service.TicketService;
import com.culture_ticket.client.ticket.common.ResponseDataDto;
import com.culture_ticket.client.ticket.common.ResponseMessageDto;
import com.culture_ticket.client.ticket.common.ResponseStatus;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * 티켓 목록 조회
     *
     * @param role
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<ResponseDataDto<Page<TicketResponseDto>>> getTickets(
        @RequestHeader("X-User-Role") String role,
        Pageable pageable
    ) {
        Page<TicketResponseDto> responseDto = ticketService.getTickets(role, pageable);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_TICKET_SUCCESS, responseDto));
    }

    /**
     * 티켓 단일 조회
     *
     * @param role
     * @param ticketId
     * @return
     */
    @GetMapping("/{ticketId}")
    public ResponseEntity<ResponseDataDto<TicketResponseDto>> getTicket(
        @RequestHeader("X-User-Role") String role,
        @PathVariable UUID ticketId
    ) {
        TicketResponseDto responseDto = ticketService.getTicket(role, ticketId);

        return ResponseEntity.ok(
            new ResponseDataDto<>(ResponseStatus.GET_TICKET_SUCCESS, responseDto));
    }

    /**
     * 티켓 삭제
     *
     * @param username
     * @param role
     * @param ticketId
     * @return
     */
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<ResponseMessageDto> deleteTicket(
        @RequestHeader("X-User-Name") String username,
        @RequestHeader("X-User-Role") String role,
        @PathVariable UUID ticketId
    ) {
        ticketService.deleteTicket(username, role, ticketId);

        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_TICKET_SUCCESS));
    }
}

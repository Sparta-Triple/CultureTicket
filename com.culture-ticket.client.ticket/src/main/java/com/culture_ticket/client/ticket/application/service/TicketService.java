package com.culture_ticket.client.ticket.application.service;

import com.culture_ticket.client.ticket.application.dto.request.KafkaTicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.response.TicketResponseDto;
import com.culture_ticket.client.ticket.common.CustomException;
import com.culture_ticket.client.ticket.common.ErrorType;
import com.culture_ticket.client.ticket.common.util.RoleValidator;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import com.culture_ticket.client.ticket.domain.repository.TicketRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * 티켓 생성
     * Feign Client 사용
     *
     * @param username
     * @param role
     * @param request
     */
    @Transactional
    public void createTicket(String userId, String username, String role,
        TicketRequestDto request) {
        RoleValidator.validateIsUser(role);

        Ticket ticket = Ticket.of(userId, request);
        ticket.created(username);
        ticketRepository.save(ticket);
    }

    /**
     * 티켓 생성
     * Kafka 사용
     *
     * @param request
     */
    @Transactional
    public void createTicket(KafkaTicketRequestDto request) {
        RoleValidator.validateIsUser(request.getRole());
        Ticket ticket = Ticket.of(request.getUserId(), request.getPerformanceId(),
            request.getSeatId(), request.getTicketPrice(), request.getReservationId());
        ticket.created(request.getUsername());
        ticketRepository.save(ticket);
    }

    /**
     * 티켓 목록 조회
     *
     * @param role
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true)
    public Page<TicketResponseDto> getTickets(String role, Pageable pageable) {
        RoleValidator.validateIsAdminOrUser(role);

        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);

        return ticketPage.map(TicketResponseDto::from);
    }

    /**
     * 티켓 단일 조회
     *
     * @param role
     * @param ticketId
     * @return
     */
    @Transactional(readOnly = true)
    public TicketResponseDto getTicket(String role, UUID ticketId) {
        RoleValidator.validateIsAdminOrUser(role);

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_TICKET));

        return TicketResponseDto.from(ticket);
    }

    /**
     * 티켓 삭제
     *
     * @param username
     * @param role
     * @param reservationId
     */
    @Transactional
    public void deleteTicket(String username, String role, UUID reservationId) {
        RoleValidator.validateIsAdminOrUser(role);

        List<Ticket> tickets = ticketRepository.findAllByReservationId(reservationId);

        if (tickets.isEmpty()) {
            throw new CustomException(ErrorType.NOT_FOUND_TICKET);
        }

        for (Ticket ticket : tickets) {
            ticket.deleted(username);
        }
    }
}

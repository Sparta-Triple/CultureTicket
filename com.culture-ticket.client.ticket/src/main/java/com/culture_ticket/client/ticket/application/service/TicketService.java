package com.culture_ticket.client.ticket.application.service;

import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.response.TicketResponseDto;
import com.culture_ticket.client.ticket.common.CustomException;
import com.culture_ticket.client.ticket.common.ErrorType;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import com.culture_ticket.client.ticket.domain.repository.TicketRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    /**
     * 티켓 생성
     *
     * @param username
     * @param role
     * @param request
     */
    @Transactional
    public void createTicket(String username, String role, TicketRequestDto request) {
        if (!role.equals("USER")) { // TODO: 나중에 권한 검증 util로 빼기
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Ticket ticket = Ticket.from(request);
        ticket.setCreatedBy(username);
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
        if (!(role.equals("ADMIN") || role.equals("USER"))) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

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
        if (!(role.equals("ADMIN") || role.equals("USER"))) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_TICKET));

        return TicketResponseDto.from(ticket);
    }

    /**
     * 티켓 삭제
     *
     * @param username
     * @param role
     * @param ticketId
     */
    @Transactional
    public void deleteTicket(String username, String role, UUID ticketId) {
        if (!(role.equals("ADMIN") || role.equals("USER"))) {
            throw new CustomException(ErrorType.ACCESS_DENIED);
        }

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() ->
            new CustomException(ErrorType.NOT_FOUND_TICKET));

        ticket.softDeletedBy(username);
    }
}

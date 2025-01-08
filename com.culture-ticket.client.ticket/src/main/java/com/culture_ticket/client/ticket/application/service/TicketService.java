package com.culture_ticket.client.ticket.application.service;

import com.culture_ticket.client.ticket.application.dto.TicketRequestDto;
import com.culture_ticket.client.ticket.common.CustomException;
import com.culture_ticket.client.ticket.common.ErrorType;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import com.culture_ticket.client.ticket.domain.repository.TicektRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicektRepository ticektRepository;

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
        ticektRepository.save(ticket);
    }
}

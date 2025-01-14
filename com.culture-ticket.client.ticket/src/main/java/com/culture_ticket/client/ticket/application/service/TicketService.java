package com.culture_ticket.client.ticket.application.service;

import com.culture_ticket.client.ticket.application.dto.request.KafkaTicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.request.TicketRequestDto;
import com.culture_ticket.client.ticket.application.dto.response.TicketResponseDto;
import com.culture_ticket.client.ticket.common.CustomException;
import com.culture_ticket.client.ticket.common.ErrorType;
import com.culture_ticket.client.ticket.common.util.RoleValidator;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import com.culture_ticket.client.ticket.domain.repository.TicketRepository;
import com.culture_ticket.client.ticket.infrastructure.messaging.PaymentRollbackProducer;
import java.util.List;
import java.util.Random;
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
    private final PaymentRollbackProducer paymentRollbackProducer;

    private static final int BETWEEN_ZERO_AND_ONE = 1;

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
        KafkaTicketRequestDto response = request;
        try {
            RoleValidator.validateIsUser(request.getRole());
            Ticket ticket = null;
            for (int i = 0; i < request.getSeatIds().size(); i++) {
                ticket = Ticket.of(request.getUserId(), request.getPerformanceId(),
                    request.getSeatIds().get(i), request.getTicketPrices().get(i), request.getReservationId());
                errorPerHalf();
                ticketRepository.save(ticket);
                ticket.created(request.getUsername());
            }
        } catch (CustomException e) {
            log.error("===== [티켓 생성 오류] -> payment-rollback, 결제 금액 :{} / {} =====", request.getTicketPrices(), e.getMessage());
            paymentRollbackProducer.rollbackPayment("payment-rollback", response);
        }
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

    // 50% 확률로 에러 발생
    private void errorPerHalf() {
        int zeroOrOne = new Random().nextInt(BETWEEN_ZERO_AND_ONE);

        if (zeroOrOne == 0) {
            throw new CustomException(ErrorType.TICKET_CREATE_PROCESSING_ERROR);
        }
    }
}

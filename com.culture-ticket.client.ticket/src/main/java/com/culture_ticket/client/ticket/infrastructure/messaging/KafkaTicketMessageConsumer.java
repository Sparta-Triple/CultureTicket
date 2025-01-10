package com.culture_ticket.client.ticket.infrastructure.messaging;

import com.culture_ticket.client.ticket.application.dto.request.KafkaTicketRequestDto;
import com.culture_ticket.client.ticket.application.service.TicketService;
import com.culture_ticket.client.ticket.common.util.RoleValidator;
import com.culture_ticket.client.ticket.domain.model.Ticket;
import com.culture_ticket.client.ticket.domain.repository.TicketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaTicketMessageConsumer {

    private final TicketService ticketService;

    @KafkaListener(topics = "ticket-topic", groupId = "ticket-creator")
    public void createTicekt(String message) {
        log.info("Received Kafka Message: -> " + message);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 사용
        Map<String, Object> requestMap;
        try {
            requestMap = objectMapper.readValue(message, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON", e);
            return; // 오류 처리
        }

        // Map에서 데이터 추출
        String userId = (String) requestMap.get("userId");
        String username = (String) requestMap.get("username");
        String role = (String) requestMap.get("role");
        UUID performanceId = UUID.fromString((String) requestMap.get("performanceId"));
        UUID seatId = UUID.fromString((String) requestMap.get("seatId"));
        Long ticketPrice = ((Number) requestMap.get("ticketPrice")).longValue();
        UUID reservationId = UUID.fromString((String) requestMap.get("reservationId"));

        KafkaTicketRequestDto requestDto = KafkaTicketRequestDto.
            of(performanceId, seatId, ticketPrice, reservationId, userId, username, role);

        ticketService.createTicket(requestDto);
    }
}
package com.culture_ticket.client.ticket.infrastructure.messaging;

import com.culture_ticket.client.ticket.application.dto.request.KafkaTicketRequestDto;
import com.culture_ticket.client.ticket.application.service.TicketService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketCreateConsumer {

    private final TicketService ticketService;

    @KafkaListener(topics = "ticket-topic", groupId = "ticket-creator")
    public void createTicekt(String message) throws JsonProcessingException {
        log.info("Received Kafka Message: -> " + message);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 사용

        KafkaTicketRequestDto requestDto = objectMapper.readValue(message,
            KafkaTicketRequestDto.class);

        ticketService.createTicket(requestDto);
    }
}
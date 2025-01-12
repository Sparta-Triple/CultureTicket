package com.culture_ticket.client.performance.infrastructure.messaging;

import com.culture_ticket.client.performance.application.dto.kafka.KafkaSeatStatusRequestDto;
import com.culture_ticket.client.performance.application.service.SeatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SeatStatusConsumer {

    private final SeatService seatService;

    @KafkaListener(topics = "seat-topic", groupId = "seat-status-changer")
    public void changeSeatStatus(String message) throws JsonProcessingException {
        log.info("Received Kafka Message (seat status): -> " + message);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 사용

        KafkaSeatStatusRequestDto requestDto = objectMapper.readValue(message,
            KafkaSeatStatusRequestDto.class);

        seatService.updateSeatsStatus(
            requestDto.getUsername(), requestDto.getSeatStatus(), requestDto.getSeatIds());
    }

}

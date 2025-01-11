package com.culture_ticket.client.reservation_payment.infrastructure.messaging;

import com.culture_ticket.client.reservation_payment.application.service.PaymentService;
import com.culture_ticket.client.reservation_payment.application.dto.kafka.KafkaTicketRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentRollbackConsumer {

    private final PaymentService paymentService;

    @KafkaListener(topics = "payment-rollback", groupId = "payment-rollback")
    public void handlePaymentRollbackEvent(String message) throws JsonProcessingException {
        log.info("======보상 트랜잭션 동작, message : {} =====", message);

        ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper 사용

        KafkaTicketRequestDto requestDto = objectMapper.readValue(message,
            KafkaTicketRequestDto.class);

        paymentService.revertPayment(requestDto);
    }
}

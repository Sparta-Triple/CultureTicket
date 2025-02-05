package com.culture_ticket.client.ticket.infrastructure.messaging;

import com.culture_ticket.client.ticket.application.dto.request.KafkaTicketRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class PaymentRollbackProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    // 주입받은 KafkaTemplate을 사용하여 Kafka에 메시지를 전송하는 send 메서드
    public KafkaTicketRequestDto rollbackPayment(String topic, KafkaTicketRequestDto requestDto){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            // SeatResponseDto 객체를 JSON 문자열로 직렬화
            jsonInString = mapper.writeValueAsString(requestDto);
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        // KafkaTemplate을 사용하여 지정된 토픽에 JSON 문자열을 전송
        kafkaTemplate.send(topic, jsonInString);

        // 로깅을 통해 전송된 데이터를 기록
        log.info("Kafka Producer send data (payment rollback) " + requestDto);

        // 전송된 SeatResponseDto 객체를 반환
        return requestDto;
    }

//    public void rollbackPayment(KafkaTicketRequestDto requestDto) {
//        kafkaTemplate.send("payment-rollback", requestDto);
//    }
}

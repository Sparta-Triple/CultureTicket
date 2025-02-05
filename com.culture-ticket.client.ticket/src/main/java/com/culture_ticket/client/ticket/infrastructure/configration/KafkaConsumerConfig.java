package com.culture_ticket.client.ticket.infrastructure.configration;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    // Kafka 컨슈머를 생성하기 위한 ConsumerFactory 빈을 정의
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String, Object> properties = new HashMap<>();

        // 부트스트랩 서버 주소 설정
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Kafka Consumer Group의 고유한 식별자를 설정
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupId");

        // 메시지 키 및 값의 디시리얼라이저를 설정
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    // 메시지를 수신하는 KafkaListenerContainerFactory 빈 정의
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(){
        // ConcurrentKafkaListenerContainerFactory를 생성
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory =
            new ConcurrentKafkaListenerContainerFactory<>();

        // 위에서 정의한 ConsumerFactory를 설정하여 KafkaListenerContainerFactory에 주입.
        kafkaListenerContainerFactory.setConsumerFactory(consumerFactory());

        // Kafka 메시지를 병렬로 처리하기 위한 설정을 추가할 수 있음(스레드 풀 크기, 에러 핸들링..)

        return kafkaListenerContainerFactory;
    }
}

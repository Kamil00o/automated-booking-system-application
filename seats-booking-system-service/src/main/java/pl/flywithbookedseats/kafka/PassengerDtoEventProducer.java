package pl.flywithbookedseats.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PassengerDtoEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(PassengerDtoEventProducer.class);

    @Value("${spring.kafka.topic-json.name}")
    private String kafkaTopicJson;
    private final KafkaTemplate<String, PassengerDtoEvent> kafkaTemplate;

    public void sendMessage(PassengerDtoEvent passengerDtoEvent) {
        logger.info("PassengerDto to send: {}", passengerDtoEvent);
        Message<PassengerDtoEvent> message = MessageBuilder
                .withPayload(passengerDtoEvent)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopicJson)
                .build();
        kafkaTemplate.send(message);
    }
}

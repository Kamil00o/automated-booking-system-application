package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;

@RequiredArgsConstructor
@Slf4j
@Service
public class PassengerDtoEventProducer {

    @Value("${spring.kafka.topic-passengerServiceEventsTopic.name}")
    private String passengerServiceEventsTopic;
    private final KafkaTemplate<String, PassengerDtoEvent> kafkaTemplate;

    public void sendMessage(PassengerDtoEvent passengerDtoEvent) {
        log.info("PassengerDto to send: {}", passengerDtoEvent);
        Message<PassengerDtoEvent> message = MessageBuilder
                .withPayload(passengerDtoEvent)
                .setHeader(KafkaHeaders.TOPIC, passengerServiceEventsTopic)
                .build();
        kafkaTemplate.send(message);
    }
}

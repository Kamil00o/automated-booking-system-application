package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.RequestedPassengerEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

@RequiredArgsConstructor
@Slf4j
@Service
public class PassengerServiceProducer {

    @Value("${spring.kafka.topic-passengerServiceEventsTopic.name}")
    private String passengerServiceEventsTopic;
    private final KafkaTemplate<String, RequestedPassengerEvent> kafkaTemplate;
    private final KafkaTemplate<String, UpdatedPassengerEvent> updatedPassengerEventKafkaTemplate;

    public void sendMessage(RequestedPassengerEvent requestedPassengerEvent) {
        log.info("PassengerDto to send: {}", requestedPassengerEvent);
        Message<RequestedPassengerEvent> message = MessageBuilder
                .withPayload(requestedPassengerEvent)
                .setHeader(KafkaHeaders.TOPIC, passengerServiceEventsTopic)
                .build();
        kafkaTemplate.send(message);
    }

    public void sendUpdatedPassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        log.info("UpdatedPassengerEvent to send: {}", updatedPassengerEvent);
        Message<UpdatedPassengerEvent> message = MessageBuilder
                .withPayload(updatedPassengerEvent)
                .setHeader(KafkaHeaders.TOPIC, passengerServiceEventsTopic)
                .build();
        updatedPassengerEventKafkaTemplate.send(message);
    }
}

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
public class BookingServiceProducer {

    @Value("${spring.kafka.topic-bookingServiceEventsTopic.name}")
    private String bookingServiceEventsTopic;
    private final KafkaTemplate<String, RequestedPassengerEvent> kafkaTemplate;
    private final KafkaTemplate<String, UpdatedPassengerEvent> updatedPassengerEventKafkaTemplate;

    public void sendMessage(RequestedPassengerEvent passengerDtoEvent) {
        log.info("PassengerDto to send: {}", passengerDtoEvent);
        Message<RequestedPassengerEvent> message = MessageBuilder
                .withPayload(passengerDtoEvent)
                .setHeader(KafkaHeaders.TOPIC, bookingServiceEventsTopic)
                .build();
        kafkaTemplate.send(message);
    }

    public void sendUpdatedPassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        log.info("UpdatedPassengerEvent to send: {}", updatedPassengerEvent);
        Message<UpdatedPassengerEvent> message = MessageBuilder
                .withPayload(updatedPassengerEvent)
                .setHeader(KafkaHeaders.TOPIC, bookingServiceEventsTopic)
                .build();
        updatedPassengerEventKafkaTemplate.send(message);
    }
}

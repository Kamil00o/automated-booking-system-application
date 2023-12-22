package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@KafkaListener(
        topics = "${spring.kafka.topic-bookingServiceEventsTopic.name}",
        groupId = "${spring.kafka.consumer.group-id}"
)
public class PassengerServiceConsumer {

    private final ConsumerAdapter consumerAdapter;

    @KafkaHandler
    public void consumeRequestedPassengerEvent(RequestedPassengerEvent requestedPassengerEvent) {
        log.info("Message received: {}", requestedPassengerEvent);
        consumerAdapter.consumeRequestedPassengerEvent(requestedPassengerEvent);
    }

    @KafkaHandler
    public void consumeUpdatedPassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        log.info("Message received: {}", updatedPassengerEvent);
        consumerAdapter.consumeUpdatePassengerEvent(updatedPassengerEvent);
    }
}

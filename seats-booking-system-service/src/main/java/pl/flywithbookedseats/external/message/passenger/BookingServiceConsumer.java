package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.RequestedPassengerEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

@Slf4j
@RequiredArgsConstructor
@Service
@KafkaListener(
        topics = "${spring.kafka.topic-passengerServiceEventsTopic.name}",
        groupId = "${spring.kafka.consumer.group-id}"
)
public class BookingServiceConsumer {

    private final ConsumerAdapter consumerAdapter;

   @KafkaHandler
    public void consumeRequestedPassengerEvent(RequestedPassengerEvent requestedPassengerEvent) {
       consumerAdapter.consumeRequestedPassengerEvent(requestedPassengerEvent);
        log.info("Message received: {}", requestedPassengerEvent);
    }

    @KafkaHandler
    public void consumeUpdatedPassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
       consumerAdapter.consumeUpdatePassengerEvent(updatedPassengerEvent);
        log.info("Message received: {}", updatedPassengerEvent);
    }
}

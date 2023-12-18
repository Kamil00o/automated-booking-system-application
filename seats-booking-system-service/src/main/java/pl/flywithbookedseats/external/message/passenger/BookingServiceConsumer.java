package pl.flywithbookedseats.external.message.passenger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.RequestedPassengerEvent;
import pl.flywithbookedseats.kafka.UpdatedPassengerEvent;

@Slf4j
@Service
@KafkaListener(
        topics = "${spring.kafka.topic-passengerServiceEventsTopic.name}",
        groupId = "${spring.kafka.consumer.group-id}"
)
public class BookingServiceConsumer {

   @KafkaHandler
    public void consume(RequestedPassengerEvent passengerDtoEvent) {
        log.info("Message received: {}", passengerDtoEvent);
    }

    @KafkaHandler
    public void consumeUpdatedPassengerEvent(UpdatedPassengerEvent updatedPassengerEvent) {
        log.info("Message received: {}", updatedPassengerEvent);
    }
}

package pl.flywithbookedseats.external.message.passenger;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.PassengerDtoEvent;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerDtoEventConsumer {

    private final ConsumerAdapter consumerAdapter;

    @KafkaListener(
            topics = "${spring.kafka.topic-toPassengerService.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(PassengerDtoEvent passengerDtoEvent) {
        log.info("Message received: {}", passengerDtoEvent);
        consumerAdapter.consumeMessage(passengerDtoEvent);
    }
}

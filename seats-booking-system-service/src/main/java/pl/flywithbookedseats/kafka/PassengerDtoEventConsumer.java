package pl.flywithbookedseats.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PassengerDtoEventConsumer {

    @KafkaListener(
            topics = "${spring.kafka.topic-toBookingService.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(PassengerDtoEvent passengerDtoEvent) {
        log.info("Message received: {}", passengerDtoEvent);
    }
}

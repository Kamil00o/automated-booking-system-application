package pl.flywithbookedseats.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PassengerDtoEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PassengerDtoEventConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic-json.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(PassengerDtoEvent passengerDtoEvent) {
        logger.info("Message received: {}", passengerDtoEvent);
        System.out.println(passengerDtoEvent.getPassengerDto().name());
    }
}

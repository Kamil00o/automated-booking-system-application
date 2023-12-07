package pl.flywithbookedseats.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//TODO:Consumer will be adjusted to receive PassengerDto from kafka topic. For now assigned topic is inapproppriate in purpose

@Service
public class PassengerDtoEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(PassengerDtoEventConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic-toBookingService.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PassengerDtoEvent passengerDtoEvent) {
        logger.info("Message received: {}", passengerDtoEvent);
    }
}

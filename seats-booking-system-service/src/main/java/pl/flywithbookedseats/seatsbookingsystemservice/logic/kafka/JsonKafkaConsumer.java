package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;

@Service
public class JsonKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PassengerDto passengerDto) {
        logger.info("Message received: {}", passengerDto);
    }
}

package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;

//TODO:Consumer will be adjusted to receive PassengerDto from kafka topic. For now assigned topic is inapproppriate in purpose

@Service
public class JsonKafkaConsumer {
    private static final Logger logger = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(PassengerDto passengerDto) {
        logger.info("Message received: {}", passengerDto);
    }
}

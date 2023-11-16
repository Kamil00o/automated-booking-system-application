package pl.flywithbookedseats.passengeraccountservice.logic.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.passengeraccountservice.logic.model.dto.PassengerAccountDto;

@Service
public class JsonPassengerAccountConsumer {

    private static final Logger logger = LoggerFactory.getLogger(JsonPassengerAccountConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic-json.name}",
            groupId = "${spring.kafka.consumer.group-id}"
            //,properties = {"spring.json.value.default.type=pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka.PassengerDtoEvent"}
    )
    public void consume(PassengerAccountDto passengerAccountDto) {
        logger.info("Message received: {}", passengerAccountDto);
    }
}

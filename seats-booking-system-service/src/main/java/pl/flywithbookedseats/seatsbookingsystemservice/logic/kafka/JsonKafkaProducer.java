package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;

@RequiredArgsConstructor
@Service
public class JsonKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private final KafkaTemplate<String, PassengerDto> kafkaTemplate;

    public void sendMessage(PassengerDto passengerDto) {
        logger.info("PassengerDto to send: {}", passengerDto);
        Message<PassengerDto> message = MessageBuilder
                .withPayload(passengerDto)
                .setHeader(KafkaHeaders.TOPIC, "kafkaTopic")
                .build();
        kafkaTemplate.send(message);
    }
}

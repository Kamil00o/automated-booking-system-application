package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JsonKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(JsonKafkaProducer.class);

    //@Value("${spring.kafka.topic-json.name}")
    private final NewTopic kafkaTopicJson;
    private final KafkaTemplate<String, PassengerDtoEvent> kafkaTemplate;

    public void sendMessage(PassengerDtoEvent passengerDtoEvent) {
        logger.info("PassengerDto to send: {}", passengerDtoEvent);
        Message<PassengerDtoEvent> message = MessageBuilder
                .withPayload(passengerDtoEvent)
                .setHeader(KafkaHeaders.TOPIC, kafkaTopicJson.name())
                .build();
        kafkaTemplate.send(message);
    }
}

package pl.flywithbookedseats.seatsbookingsystemservice.logic.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Value("${spring.kafka.topic.name}")
    private String kafkaTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        logger.info("The following message has been sent: {}", message);
        kafkaTemplate.send(kafkaTopic, message);
    }
}

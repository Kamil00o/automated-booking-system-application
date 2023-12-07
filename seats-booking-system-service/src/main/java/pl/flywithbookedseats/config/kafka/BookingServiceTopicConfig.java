package pl.flywithbookedseats.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class BookingServiceTopicConfig {

    @Value("${spring.kafka.topic-toBookingService.name}")
    private String toBookingServiceTopic;

    @Value("${spring.kafka.topic-toPassengerService.name}")
    private String toPassengerServiceTopic;

    @Bean
    public NewTopic toBookingServiceTopic() {
        return TopicBuilder.name(toBookingServiceTopic)
                .build();
    }

    @Bean
    public NewTopic toPassengerServiceTopic() {
        return TopicBuilder.name(toPassengerServiceTopic)
                .build();
    }
}

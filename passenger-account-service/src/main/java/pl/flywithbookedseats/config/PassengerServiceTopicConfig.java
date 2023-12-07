package pl.flywithbookedseats.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PassengerServiceTopicConfig {

    @Value("${spring.kafka.topic-toPassengerService.name}")
    private String toPassengerServiceTopic;

    @Value("${spring.kafka.topic-toBookingService.name}")
    private String toBookingServiceTopic;

    @Bean
    public NewTopic toPassengerServiceTopic() {
        return TopicBuilder.name(toPassengerServiceTopic)
                .build();
    }

    @Bean
    public NewTopic toBookingServiceTopic() {
        return TopicBuilder.name(toBookingServiceTopic)
                .build();
    }
}

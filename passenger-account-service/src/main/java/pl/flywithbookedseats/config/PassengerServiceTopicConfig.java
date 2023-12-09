package pl.flywithbookedseats.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PassengerServiceTopicConfig {

    @Value("${spring.kafka.topic-bookingServiceEventsTopic.name}")
    private String bookingServiceEventsTopic;

    @Value("${spring.kafka.topic-passengerServiceEventsTopic.name}")
    private String passengerServiceEventsTopic;

    @Bean
    public NewTopic bookingServiceEventsTopic() {
        return TopicBuilder.name(bookingServiceEventsTopic)
                .build();
    }

    @Bean
    public NewTopic passengerServiceEventsTopic() {
        return TopicBuilder.name(passengerServiceEventsTopic)
                .build();
    }
}

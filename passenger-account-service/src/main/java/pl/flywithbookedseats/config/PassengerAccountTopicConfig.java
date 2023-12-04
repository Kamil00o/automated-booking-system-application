package pl.flywithbookedseats.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class PassengerAccountTopicConfig {

    @Value("${spring.kafka.topic-json.name}")
    private String kafkaTopicJson;

    @Bean
    public NewTopic kafkaTopicJsonTopic() {
        return TopicBuilder.name(kafkaTopicJson)
                .build();
    }
}

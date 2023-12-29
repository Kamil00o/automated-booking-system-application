package pl.flywithbookedseats.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.passenger.BookingService;
import pl.flywithbookedseats.domain.passenger.PassengerRepository;
import pl.flywithbookedseats.domain.passenger.ProducerService;
import pl.flywithbookedseats.external.message.passenger.KafkaPassengerDtoMapper;
import pl.flywithbookedseats.external.message.passenger.PassengerServiceProducer;
import pl.flywithbookedseats.external.message.passenger.ProducerAdapter;
import pl.flywithbookedseats.external.service.passenger.BookingAdapter;
import pl.flywithbookedseats.external.service.passenger.FeignBookingService;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.external.service.passenger.FeignPassengerDtoMapper;
import pl.flywithbookedseats.external.storage.passenger.PassengerStorageAdapter;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntityMapper;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class PassengerServiceDomainConfiguration {

    @Bean
    public PassengerService passengerService(
            PassengerRepository passengerRepository,
            BookingService bookingService,
            ProducerService producerService) {
        return new PassengerService(passengerRepository, bookingService, producerService);
    }

    @Bean
    public PassengerRepository passengerRepository(
            PassengerEntityMapper mapper,
            JpaPassengerRepository jpaPassengerRepository) {
        return new PassengerStorageAdapter(mapper, jpaPassengerRepository);
    }

    @Bean
    public BookingService bookingService(
            FeignBookingService feignBookingService,
            FeignPassengerDtoMapper feignPassengerDtoMapper) {
        return new BookingAdapter(feignBookingService, feignPassengerDtoMapper);
    }

    @Bean
    public ProducerService producerService(
            PassengerServiceProducer passengerServiceProducer,
            KafkaPassengerDtoMapper kafkaPassengerDtoMapper
    ) {
        return new ProducerAdapter(passengerServiceProducer, kafkaPassengerDtoMapper);
    }
}

package pl.flywithbookedseats.passengeraccountservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.BookingService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerService;
import pl.flywithbookedseats.passengeraccountservice.external.service.passenger.BookingAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.service.passenger.BookingPassengerDtoProxy;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerServiceImpl;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.PassengerStorageAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.PassengerEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.JpaPassengerRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public PassengerService passengerAccountService(
            PassengerBusinessLogic passengerBusinessLogic,
            PassengerRepository passengerRepository) {
        return new PassengerServiceImpl(passengerBusinessLogic, passengerRepository);
    }

    @Bean
    public PassengerBusinessLogic passengerAccountBusinessLogic(
            PassengerRepository passengerRepository,
            BookingService bookingService) {
        return new PassengerBusinessLogic(passengerRepository,
                bookingService);
    }

    @Bean
    public PassengerRepository passengerAccountRepository(
            PassengerEntityMapper mapper,
            JpaPassengerRepository jpaPassengerRepository) {
        return new PassengerStorageAdapter(mapper, jpaPassengerRepository);
    }

    @Bean
    public BookingService bookingPassengerDtoProxyService(
            BookingPassengerDtoProxy bookingPassengerDtoProxy,
            PassengerDtoMapper passengerDtoMapper) {
        return new BookingAdapter(bookingPassengerDtoProxy, passengerDtoMapper);
    }
}

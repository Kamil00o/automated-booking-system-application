package pl.flywithbookedseats.passengeraccountservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerService;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter.BookingDtoProxyAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.BookingPassengerDtoProxy;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerServiceImpl;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter.PassengerStorageAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerRepository;

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
            BookingDtoProxyService bookingDtoProxyService) {
        return new PassengerBusinessLogic(passengerRepository,
                bookingDtoProxyService);
    }

    @Bean
    public PassengerRepository passengerAccountRepository(
            PassengerEntityMapper mapper,
            JpaPassengerRepository jpaPassengerRepository) {
        return new PassengerStorageAdapter(mapper, jpaPassengerRepository);
    }

    @Bean
    public BookingDtoProxyService bookingPassengerDtoProxyService(
            BookingPassengerDtoProxy bookingPassengerDtoProxy,
            PassengerDtoMapper passengerDtoMapper) {
        return new BookingDtoProxyAdapter(bookingPassengerDtoProxy, passengerDtoMapper);
    }
}

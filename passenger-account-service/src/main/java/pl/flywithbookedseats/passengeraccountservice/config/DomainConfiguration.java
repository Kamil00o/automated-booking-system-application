package pl.flywithbookedseats.passengeraccountservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.PassengerAccountDtoMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.BookingPassengerDtoProxyService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter.BookingPassengerDtoProxyAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.BookingPassengerDtoProxy;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountServiceImpl;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter.PassengerAccountStorageAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public PassengerAccountService passengerAccountService(PassengerAccountBusinessLogic passengerAccountBusinessLogic,
                                                           PassengerAccountRepository passengerAccountRepository) {
        return new PassengerAccountServiceImpl(passengerAccountBusinessLogic, passengerAccountRepository);
    }

    @Bean
    public PassengerAccountBusinessLogic passengerAccountBusinessLogic(
            JpaPassengerAccountRepository jpaPassengerAccountRepository,
            PassengerAccountRepository passengerAccountRepository,
            BookingPassengerDtoProxyService bookingPassengerDtoProxyService) {
        return new PassengerAccountBusinessLogic(jpaPassengerAccountRepository,
                passengerAccountRepository,
                bookingPassengerDtoProxyService);
    }

    @Bean
    public PassengerAccountRepository passengerAccountRepository(
            PassengerAccountEntityMapper mapper,
            JpaPassengerAccountRepository jpaPassengerAccountRepository) {
        return new PassengerAccountStorageAdapter(mapper, jpaPassengerAccountRepository);
    }

    @Bean
    public BookingPassengerDtoProxyService bookingPassengerDtoProxyService(
            BookingPassengerDtoProxy bookingPassengerDtoProxy,
            PassengerAccountDtoMapper passengerAccountDtoMapper) {
        return new BookingPassengerDtoProxyAdapter(bookingPassengerDtoProxy, passengerAccountDtoMapper);
    }
}

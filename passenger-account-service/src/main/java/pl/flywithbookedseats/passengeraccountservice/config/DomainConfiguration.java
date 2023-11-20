package pl.flywithbookedseats.passengeraccountservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper.CreatePassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountRepository;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.PassengerAccountService;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.BookingPassengerDtoProxy;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountBusinessLogic;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.service.implementation.PassengerAccountServiceImpl;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.adapter.PassengerAccountStorageAdapter;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.DtoPassengerAccountEntityMapper;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.mapper.PassengerAccountEntityMapper1;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.repository.JpaPassengerAccountRepository;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public PassengerAccountService passengerAccountService(PassengerAccountBusinessLogic passengerAccountBusinessLogic) {
        return new PassengerAccountServiceImpl(passengerAccountBusinessLogic);
    }

    @Bean
    public PassengerAccountBusinessLogic passengerAccountBusinessLogic(
            JpaPassengerAccountRepository jpaPassengerAccountRepository,
            CreatePassengerAccountEntityMapper createPassengerAccountEntityMapper,
            DtoPassengerAccountEntityMapper dtoPassengerAccountEntityMapper,
            BookingPassengerDtoProxy bookingPassengerDtoProxy,
            PassengerAccountRepository passengerAccountRepository) {
        return new PassengerAccountBusinessLogic(jpaPassengerAccountRepository,
                createPassengerAccountEntityMapper,
                dtoPassengerAccountEntityMapper,
                bookingPassengerDtoProxy, passengerAccountRepository);
    }

    @Bean
    public PassengerAccountRepository passengerAccountRepository(
            PassengerAccountEntityMapper1 mapper1,
            JpaPassengerAccountRepository jpaPassengerAccountRepository) {
        return new PassengerAccountStorageAdapter(mapper1, jpaPassengerAccountRepository);
    }

    @Bean
    public BookingPassengerDtoProxy bookingPassengerDtoProxy() {
        return new BookingPassengerDtoProxy() {
            @Override
            public PassengerAccountDto getPassengerDtoData(String email) {
                return null;
            }
        };
    }

    /*public BookingPassengerDtoProxy bookingPassengerDtoProxy() {
        return new BookingPassengerDtoProxy();
    }*/
}

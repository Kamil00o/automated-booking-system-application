package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.flight.FlightRepository;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.flight.FlightAdapterRepository;
import pl.flywithbookedseats.external.storage.flight.JpaFlightEntityMapper;
import pl.flywithbookedseats.external.storage.flight.JpaFlightRepository;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepository;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepositoryMapper;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeStorageAdapter;
import pl.flywithbookedseats.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.api.reservation.ReservationDtoMapper1;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerServiceImpl;
import pl.flywithbookedseats.domain.reservation.ReservationBusinessLogic;

@Configuration
public class BookingServiceDomainConfiguration {

    @Bean
    public SeatsSchemeService seatsSchemeService(
            SeatsSchemeRepository seatsSchemeRepository) {
        return new SeatsSchemeService(seatsSchemeRepository);
    }

    @Bean
    public SeatsSchemeRepository seatsSchemeRepository(
            JpaSeatsSchemeRepository jpaSeatsSchemeRepository,
            JpaSeatsSchemeRepositoryMapper jpaSeatsSchemeRepositoryMapper) {
        return new SeatsSchemeStorageAdapter(jpaSeatsSchemeRepository, jpaSeatsSchemeRepositoryMapper);
    }

    @Bean
    public FlightService flightService(
            FlightRepository flightRepository,
            SeatsSchemeService seatsSchemeService,
            PassengerServiceImpl passengerServiceImpl) {
        return new FlightService(flightRepository, seatsSchemeService, passengerServiceImpl);
    }

    @Bean
    public FlightRepository flightRepository(
            JpaFlightRepository jpaFlightRepository,
            JpaFlightEntityMapper jpaFlightEntityMapper
    ) {
        return new FlightAdapterRepository(jpaFlightRepository, jpaFlightEntityMapper);
    }

    @Bean
    public SeatsBookingService seatsBookingService(
            FlightService flightService,
            PassengerBusinessLogic passengerBusinessLogic,
            ReservationBusinessLogic reservationBusinessLogic,
            ReservationDtoMapper1 reservationDtoMapper1,
            PassengerDtoMapper passengerDtoMapper
            ) {
        return new SeatsBookingService(
                flightService,
                passengerBusinessLogic,
                reservationBusinessLogic,
                reservationDtoMapper1,
                passengerDtoMapper
        );
    }
}

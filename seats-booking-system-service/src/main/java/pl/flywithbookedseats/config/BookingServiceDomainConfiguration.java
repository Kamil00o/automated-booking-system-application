package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.flight.FlightRepository;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.passenger.PassengerRepository;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.reservation.ReservationRepository;
import pl.flywithbookedseats.domain.reservation.ReservationService;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.flight.FlightStorageAdapter;
import pl.flywithbookedseats.external.storage.flight.JpaFlightEntityMapper;
import pl.flywithbookedseats.external.storage.flight.JpaFlightRepository;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepository;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepositoryMapper;
import pl.flywithbookedseats.external.storage.passenger.PassengerStorageAdapter;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepository;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepositoryMapper;
import pl.flywithbookedseats.external.storage.reservation.ReservationStorageAdapter;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepository;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepositoryMapper;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeStorageAdapter;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper1;
import pl.flywithbookedseats.domain.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.domain.passenger.PassengerService1Impl;

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
            PassengerService1Impl passengerServiceImpl) {
        return new FlightService(flightRepository, seatsSchemeService, passengerServiceImpl);
    }

    @Bean
    public FlightRepository flightRepository(
            JpaFlightRepository jpaFlightRepository,
            JpaFlightEntityMapper jpaFlightEntityMapper
    ) {
        return new FlightStorageAdapter(jpaFlightRepository, jpaFlightEntityMapper);
    }

    @Bean
    public SeatsBookingService seatsBookingService(
            FlightService flightService,
            PassengerBusinessLogic passengerBusinessLogic,
            ReservationService reservationService,
            PassengerDtoMapper1 passengerDtoMapper1,
            JpaReservationRepositoryMapper jpaReservationRepositoryMapper
            ) {
        return new SeatsBookingService(
                flightService,
                passengerBusinessLogic,
                reservationService,
                passengerDtoMapper1,
                jpaReservationRepositoryMapper
        );
    }

    @Bean
    public ReservationService reservationService(
            ReservationRepository reservationRepository,
            PassengerBusinessLogic passengerBusinessLogic
    ) {
        return new ReservationService(reservationRepository, passengerBusinessLogic);
    }

    @Bean
    public ReservationRepository reservationRepository(
            JpaReservationRepositoryMapper jpaReservationRepositoryMapper,
            JpaReservationRepository jpaReservationRepository
    ) {
        return new ReservationStorageAdapter(jpaReservationRepositoryMapper, jpaReservationRepository);
    }

    @Bean
    public PassengerService passengerService(
            PassengerRepository passengerRepository
    ) {
        return new PassengerService(passengerRepository);
    }

    @Bean
    public PassengerRepository passengerRepository(
            JpaPassengerRepository jpaPassengerRepository,
            JpaPassengerRepositoryMapper jpaPassengerRepositoryMapper
    ) {
        return new PassengerStorageAdapter(jpaPassengerRepository, jpaPassengerRepositoryMapper);
    }
}

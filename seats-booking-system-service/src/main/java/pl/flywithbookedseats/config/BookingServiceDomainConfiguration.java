package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.flight.FlightRepository;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.passenger.*;
import pl.flywithbookedseats.domain.reservation.ReservationRepository;
import pl.flywithbookedseats.domain.reservation.ReservationService;
import pl.flywithbookedseats.domain.seatsbookingsystem.SeatsBookingService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.message.passenger.BookingServiceProducer;
import pl.flywithbookedseats.external.message.passenger.KafkaPassengerDtoMapper;
import pl.flywithbookedseats.external.message.passenger.ProducerServiceAdapter;
import pl.flywithbookedseats.external.service.passenger.FeignPassengerDtoMapper;
import pl.flywithbookedseats.external.service.passenger.FeignPassengerService;
import pl.flywithbookedseats.external.service.passenger.PassengerAccountServiceAdapter;
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
            PassengerService passengerService) {
        return new FlightService(flightRepository, seatsSchemeService, passengerService);
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
            PassengerService passengerService,
            ReservationService reservationService
            ) {
        return new SeatsBookingService(
                flightService,
                passengerService,
                reservationService
        );
    }

    @Bean
    public ReservationService reservationService(
            ReservationRepository reservationRepository,
            PassengerService passengerService
    ) {
        return new ReservationService(reservationRepository, passengerService);
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
            PassengerRepository passengerRepository,
            PassengerAccountService passengerAccountService,
            ProducerService producerService
    ) {
        return new PassengerService(passengerRepository, passengerAccountService, producerService);
    }

    @Bean
    public PassengerRepository passengerRepository(
            JpaPassengerRepository jpaPassengerRepository,
            JpaPassengerRepositoryMapper jpaPassengerRepositoryMapper
    ) {
        return new PassengerStorageAdapter(jpaPassengerRepository, jpaPassengerRepositoryMapper);
    }

    @Bean
    public PassengerAccountService passengerAccountService(
            FeignPassengerService feignPassengerService,
            FeignPassengerDtoMapper feignPassengerDtoMapper
    ) {
        return new PassengerAccountServiceAdapter(feignPassengerService, feignPassengerDtoMapper);
    }

    @Bean
    public ProducerService producerService(
            BookingServiceProducer bookingServiceProducer,
            KafkaPassengerDtoMapper kafkaPassengerDtoMapper
    ) {
        return new ProducerServiceAdapter(bookingServiceProducer, kafkaPassengerDtoMapper);
    }
}

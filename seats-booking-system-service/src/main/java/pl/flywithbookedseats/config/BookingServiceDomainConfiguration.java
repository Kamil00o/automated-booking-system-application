package pl.flywithbookedseats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.flywithbookedseats.domain.flight.FlightRepository;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.flight.FlightAdapterRepository;
import pl.flywithbookedseats.external.storage.flight.JpaFlightEntityMapper;
import pl.flywithbookedseats.external.storage.flight.JpaFlightRepository;
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
            SeatsSchemeService seatsSchemeService) {
        return new FlightService(flightRepository, seatsSchemeService);
    }

    @Bean
    public FlightRepository flightRepository(
            JpaFlightRepository jpaFlightRepository,
            JpaFlightEntityMapper jpaFlightEntityMapper
    ) {
        return new FlightAdapterRepository(jpaFlightRepository, jpaFlightEntityMapper);
    }
}

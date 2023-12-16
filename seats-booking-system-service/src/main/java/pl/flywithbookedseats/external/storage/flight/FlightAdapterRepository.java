package pl.flywithbookedseats.external.storage.flight;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.domain.flight.FlightRepository;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID;

@RequiredArgsConstructor
public class FlightAdapterRepository implements FlightRepository {

    private final JpaFlightRepository repository;
    private final JpaFlightEntityMapper mapper;
    @Override
    public boolean existsByFlightName(String flightName) {
        return repository.existsByFlightName(flightName);
    }

    @Override
    public Flight save(Flight flight) {
        FlightEntity flightEntity = repository.save(mapper.toEntity(flight));
        return mapper.toDomain(flightEntity);
    }

    @Override
    public Flight findByFlightName(String flightName) {
        FlightEntity foundFlightEntity = repository
                .findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));
        return mapper.toDomain(foundFlightEntity);
    }

    @Override
    public Flight findByFlightServiceId(Long flightServiceId) {
        FlightEntity foundFlightEntity = repository
                .findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));
        return mapper.toDomain(foundFlightEntity);
    }
}

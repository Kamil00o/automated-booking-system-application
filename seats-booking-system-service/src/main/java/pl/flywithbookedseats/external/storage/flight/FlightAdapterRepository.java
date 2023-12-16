package pl.flywithbookedseats.external.storage.flight;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightRepository;

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
}

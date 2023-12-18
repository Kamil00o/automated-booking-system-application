package pl.flywithbookedseats.external.storage.flight;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.domain.flight.FlightRepository;
import pl.flywithbookedseats.domain.flight.PageFlight;

import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID;

@RequiredArgsConstructor
public class FlightStorageAdapter implements FlightRepository {

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

    @Override
    public void deleteByFlightServiceId(Long flightServiceId) {
        repository.deleteByFlightServiceId(flightServiceId);
    }

    @Override
    public void deleteByFlightName(String flightName) {
        repository.deleteByFlightName(flightName);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public PageFlight findAll(Pageable pageable) {
        Page<FlightEntity> pageOfFlightEntities = repository.findAll(pageable);
        List<Flight> flightsOnCurrentPage = pageOfFlightEntities
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageFlight(flightsOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfFlightEntities.getTotalPages(),
                pageOfFlightEntities.getTotalElements());
    }
}

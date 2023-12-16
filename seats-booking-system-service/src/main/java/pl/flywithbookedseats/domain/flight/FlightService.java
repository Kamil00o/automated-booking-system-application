package pl.flywithbookedseats.domain.flight;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;

import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_ALREADY_EXISTS_FLIGHT_NAME;

@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository repository;
    private final SeatsSchemeService seatsSchemeService;

    public Flight createNewFlight(Flight flight) {
        if (!exists(flight)) {
            return generateNewFlight(flight);
        } else {
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(flight.getFlightName()));
        }
    }

    public Flight generateNewFlight(Flight flight) {
        retrieveSeatsSchemeForPlaneTypeIfNeeded(flight);
        return repository.save(flight);
    }

    private void retrieveSeatsSchemeForPlaneTypeIfNeeded(Flight flight) {
        Map<String, String> savedSeatsSchemeMap;
        Map<String, Long> generatedBookedSeatsInPlaneMap;
        if (flight.getBookedSeatsInPlaneMap() == null) {

            savedSeatsSchemeMap = new TreeMap<>(seatsSchemeService
                    .retrieveSeatsSchemeModelByPlaneModel(flight.getPlaneTypeName()).getSeatsSchemeMap());
            generatedBookedSeatsInPlaneMap = createReservedSeatsSchemeMap(savedSeatsSchemeMap);
            flight.setBookedSeatsInPlaneMap(generatedBookedSeatsInPlaneMap);
            log.debug("Created Flight: {}", flight.toString());
        }
    }

    private Map<String, Long> createReservedSeatsSchemeMap(Map<String, String> savedSeatsSchemeMap) {
        Map<String, Long> localBookedSeatsInPlaneMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : savedSeatsSchemeMap.entrySet()) {
            StringBuilder fullSeatName = new StringBuilder();
            fullSeatName.append(entry.getValue())
                    .append(" ")
                    .append(entry.getKey());
            localBookedSeatsInPlaneMap.put(fullSeatName.toString(), 0L);
            fullSeatName.delete(0, fullSeatName.length());
        }

        return localBookedSeatsInPlaneMap;
    }

    public boolean exists(Flight flight) {
        return repository.existsByFlightName(flight.getFlightName());
    }
}

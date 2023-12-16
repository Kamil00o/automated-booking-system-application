package pl.flywithbookedseats.domain.flight;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.common.Consts.SEAT_PASSENGER_DATA_UNAVAILABLE;
import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_ALREADY_EXISTS_FLIGHT_NAME;

@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository repository;
    private final SeatsSchemeService seatsSchemeService;
    private final PassengerServiceImpl passengerService;

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

    public Map<String, String> convertBookedSeatsInPlaneMapToDtoVersion(Map<String, Long> bookedSeatsInPlaneMap) {
        Map<String, String> convertedBookedSeatsInPlaneMap = new TreeMap<String, String>();
        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
            convertedBookedSeatsInPlaneMap.put(entry.getKey(), retrievePassengerNameSurname(entry.getValue()));
        }
        return convertedBookedSeatsInPlaneMap;
    }

    private String retrievePassengerNameSurname(Long passengerId) {
        StringBuilder passengerNameSurname = new StringBuilder();
        if (passengerId != null && passengerId != 0L) {
            Passenger savedPassenger = retrievePassengerEntityFromDb(passengerId);
            if (savedPassenger != null) {
                passengerNameSurname.append(savedPassenger.getName())
                        .append(" ")
                        .append(savedPassenger.getSurname());
            } else {
                passengerNameSurname.append(SEAT_PASSENGER_DATA_UNAVAILABLE);
            }

            return passengerNameSurname.toString();
        } else {
            passengerNameSurname.append("free");
            return passengerNameSurname.toString();
        }
    }

    private Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerService.retrievePassengerById(passengerId);
    }

    public boolean exists(Flight flight) {
        return repository.existsByFlightName(flight.getFlightName());
    }
}

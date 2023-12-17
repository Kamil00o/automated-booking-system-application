package pl.flywithbookedseats.domain.flight;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static pl.flywithbookedseats.common.Consts.SEAT_PASSENGER_DATA_UNAVAILABLE;
import static pl.flywithbookedseats.domain.flight.FlightConstImpl.*;

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

    public PageFlight retrieveAllFlightsFromDb(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Flight retrieveFlightByFlightName(String flightName) {
        return repository.findByFlightName(flightName);
    }

    public Flight retrieveFlightByFlightServiceId(Long flightServiceId) {
        return repository.findByFlightServiceId(flightServiceId);
    }

    public Flight updateFlightByFlightName(String flightName, Flight flight) {
        Flight savedFlight = retrieveFlightByFlightName(flightName);
        return updateSpecifiedFlight(flight, savedFlight);
    }

    public Flight updateFlightByFlightServiceId(Long id, Flight flight) {
        Flight savedFlight = retrieveFlightByFlightServiceId(id);
        return updateSpecifiedFlight(flight, savedFlight);
    }

    private Flight updateSpecifiedFlight(Flight flightUpdateData, Flight flightToUpdate) {
        if (!exists(flightUpdateData, flightToUpdate)) {
            flightToUpdate.setFlightName(flightUpdateData.getFlightName());
            flightToUpdate.setPlaneTypeName(flightUpdateData.getPlaneTypeName());
            flightToUpdate.setFlightServiceId(flightUpdateData.getFlightServiceId());
            setBookedSeatsInPlaneMapIfPossible(flightUpdateData.getBookedSeatsInPlaneMap(), flightToUpdate);
            repository.save(flightToUpdate);
            log.info(FLIGHT_UPDATED.formatted(flightToUpdate.getFlightName()));
            return flightToUpdate;
        } else {
            log.warn(FLIGHT_NOT_UPDATED.formatted(flightToUpdate.getFlightServiceId()));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(flightUpdateData.getFlightName()));
        }
    }

    public Map<String, String> convertBookedSeatsInPlaneMapToDtoVersion(Map<String, Long> bookedSeatsInPlaneMap) {
        Map<String, String> convertedBookedSeatsInPlaneMap = new TreeMap<String, String>();
        for (Map.Entry<String, Long> entry : bookedSeatsInPlaneMap.entrySet()) {
            convertedBookedSeatsInPlaneMap.put(entry.getKey(), retrievePassengerNameSurname(entry.getValue()));
        }
        return convertedBookedSeatsInPlaneMap;
    }

    public void deleteAllFlights() {
        repository.deleteAll();
    }

    public void deleteFlightByFlightName(String flightName) {
        repository.deleteByFlightName(flightName);
    }

    public void deleteFlightByFlyServiceId(Long flightServiceId) {
        repository.deleteByFlightServiceId(flightServiceId);
    }

    private void setBookedSeatsInPlaneMapIfPossible(Map<String, Long> bookedSeatsInPlaneMapToSet
            , Flight flightToUpdate) {
        if (bookedSeatsInPlaneMapToSet != null) {
            if (!bookedSeatsInPlaneMapToSet.isEmpty()) {
                flightToUpdate.setBookedSeatsInPlaneMap(bookedSeatsInPlaneMapToSet);
            }
        }
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

    private boolean exists(Flight flight) {
        return repository.existsByFlightName(flight.getFlightName());
    }

    private boolean exists(Flight flightToUpdate, Flight flightUpdateData) {
        String flightName = flightUpdateData.getFlightName();
        if (repository.existsByFlightName(flightName)) {
            return Objects.equals(retrieveFlightByFlightName(flightName).getFlightServiceId(), flightToUpdate.getFlightServiceId());
        }

        return false;
    }
}

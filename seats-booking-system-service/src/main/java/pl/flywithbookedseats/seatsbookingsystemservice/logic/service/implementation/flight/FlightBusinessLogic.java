package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightNotCreatedException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.FlightRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.SeatsSchemeModelRepository;

import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightConstImpl.SEATS_SCHEME_NOT_FOUND_FLIGHT_NOT_CREATED_EXCEPTION;

@AllArgsConstructor
@Component
public class FlightBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(FlightBusinessLogic.class);

    private final FlightRepository flightRepository;
    private final SeatsSchemeModelRepository seatsSchemeModelRepository;

    public void retrieveSeatsSchemeForPlaneTypeIfNeeded(Flight flight) {
        Map<String, String> savedSeatsSchemeMap;
        Map<String, String> generatedBookedSeatsInPlaneMap;
        String planeTypeName = flight.getPlaneTypeName();
        if (flight.getBookedSeatsInPlaneMap() == null) {
            SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository
                    .findByPlaneModelName(planeTypeName)
                    .orElseThrow(() -> new FlightNotCreatedException(SEATS_SCHEME_NOT_FOUND_FLIGHT_NOT_CREATED_EXCEPTION
                            .formatted(planeTypeName, flight.getFlightName())));
            savedSeatsSchemeMap = new TreeMap<>(savedSeatsSchemeModel.getSeatsSchemeMap());
            generatedBookedSeatsInPlaneMap = createReservedSeatsSchemeMap(savedSeatsSchemeMap);
            flight.setBookedSeatsInPlaneMap(generatedBookedSeatsInPlaneMap);
        }
    }

    public Map<String, String> createReservedSeatsSchemeMap(Map<String, String> savedSeatsSchemeMap) {
        Map<String, String> localBookedSeatsInPlaneMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : savedSeatsSchemeMap.entrySet()) {
            StringBuilder fullSeatName = new StringBuilder();
            fullSeatName.append(entry.getValue())
                    .append(" ")
                    .append(entry.getKey());
            localBookedSeatsInPlaneMap.put(fullSeatName.toString(), "empty");
            fullSeatName.delete(0, fullSeatName.length());
        }

        return localBookedSeatsInPlaneMap;
    }

    public boolean exists(CreateFlightCommand createFlightCommand) {
        return flightRepository.existsByFlightName(createFlightCommand.flightName());
    }

    public boolean exists(UpdateFlightCommand updateFlightCommand) {
        return flightRepository.existsByFlightName(updateFlightCommand.flightName());
    }

    public boolean existsByFlightServiceId(UpdateFlightCommand updateFlightCommand) {
        return flightRepository.existsByFlightServiceId(updateFlightCommand.flightServiceId());
    }
}

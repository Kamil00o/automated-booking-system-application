package pl.flywithbookedseats.domain.flight;

import pl.flywithbookedseats.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.api.flight.FlightDto;

import java.util.List;

public interface FlightService {

    FlightDto createNewFlight(CreateFlightCommand createFlightCommand);

    FlightDto updateFlightByFlightName(UpdateFlightCommand updateFlightCommand, String flightName);

    FlightDto updateFlightByFlightServiceId(UpdateFlightCommand updateFlightCommand, Long FlightServiceId);

    List<FlightDto> retrieveAllFlightsFromDb();

    FlightDto retrieveFlightByFlightName(String flightName);

    FlightDto retrieveFlightByFlightServiceId(Long flightServiceId);

    void deleteAllFlights();

    void deleteFlightByFlightName(String flightName);

    void deleteFlightByFlyServiceId(Long flightServiceId);
}

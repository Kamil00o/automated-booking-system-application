package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.flight.FlightDto;
import pl.flywithbookedseats.domain.flight.Flight;
import pl.flywithbookedseats.domain.flight.FlightService;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlightApplicationService {

    private final FlightService service;

    @Transactional
    public Flight createNewFlight(Flight flight) {
        return service.createNewFlight(flight);
    }

    @Transactional
    public Flight updateFlightByFlightName(Flight flight, String flightName) {
        return service.updateFlightByFlightName(flightName, flight);
    }

    @Transactional
    public Flight updateFlightByFlightServiceId(Flight flight, Long flightServiceId) {
        return service.updateFlightByFlightServiceId(flightServiceId, flight);
    }

    public List<FlightDto> retrieveAllFlightsFromDb() {
        return null;
    }

    public Flight retrieveFlightByFlightName(String flightName) {
        return service.retrieveFlightByFlightName(flightName);
    }

    public Flight retrieveFlightByFlightServiceId(Long flightServiceId) {
        return service.retrieveFlightByFlightServiceId(flightServiceId);
    }

    @Transactional
    public void deleteAllFlights() {

    }

    @Transactional
    public void deleteFlightByFlightName(String flightName) {

    }

    @Transactional
    public void deleteFlightByFlyServiceId(Long flightServiceId) {

    }

    public Map<String, String> convertBookedSeatsInPlaneMapToDtoVersion(Map<String, Long> bookedSeatsInPlaneMap) {
        return service.convertBookedSeatsInPlaneMapToDtoVersion(bookedSeatsInPlaneMap);
    }
}

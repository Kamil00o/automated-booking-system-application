package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.flight.FlightDto;
import pl.flywithbookedseats.domain.flight.Flight;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FlightApplicationService {

    @Transactional
    public Flight createNewFlight(Flight flight) {
        return null;
    }

    @Transactional
    public Flight updateFlightByFlightName(Flight flight, String flightName) {
        return null;
    }

    @Transactional
    public Flight updateFlightByFlightServiceId(Flight flight, Long FlightServiceId) {
        return null;
    }

    public List<FlightDto> retrieveAllFlightsFromDb() {
        return null;
    }

    public Flight retrieveFlightByFlightName(String flightName) {
        return null;
    }

    public Flight retrieveFlightByFlightServiceId(Long flightServiceId) {
        return null;
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
}

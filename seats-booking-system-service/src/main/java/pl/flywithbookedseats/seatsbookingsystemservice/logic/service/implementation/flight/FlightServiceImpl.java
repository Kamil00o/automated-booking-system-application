package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightNotCreatedException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.CreateFlightMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.FlightDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.FlightDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.FlightRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.SeatsSchemeModelRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.FlightService;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightConstImpl.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final SeatsSchemeModelRepository seatsSchemeModelRepository;
    private final CreateFlightMapper createFlightMapper;
    private final FlightDtoMapper flightDtoMapper;

    @Transactional
    @Override
    public FlightDto createNewFlight(CreateFlightCommand createFlightCommand) {
        String flightName = createFlightCommand.flightName();
        if (!exists(createFlightCommand)) {
            Flight newFlight = createFlightMapper.apply(createFlightCommand);
            retrieveSeatsSchemeForPlaneTypeIfNeeded(newFlight);
            flightRepository.save(newFlight);
            return flightDtoMapper.apply(newFlight);
        } else {
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME.formatted(flightName));
        }
    }

    @Transactional
    @Override
    public FlightDto updateFlightByFlightName(UpdateFlightCommand updateFlightCommand, String flightName) {
        return null;
    }

    @Transactional
    @Override
    public FlightDto updateFlightByFlightServiceId(UpdateFlightCommand updateFlightCommand, Long FlightServiceId) {
        return null;
    }

    @Transactional
    @Override
    public List<FlightDto> retrieveAllFlightsFromDb() {
        return null;
    }

    @Override
    public FlightDto retrieveFlightByFlightName(String flightName) {
        return null;
    }

    @Override
    public FlightDto retrieveFlightByFlightServiceId(Long flightServiceId) {
        return null;
    }

    @Transactional
    @Override
    public void deleteAllFlights() {

    }

    @Transactional
    @Override
    public void deleteFlightByFlightName(String flightName) {

    }

    @Transactional
    @Override
    public void deleteFlightByFlyServiceId(Long flightServiceId) {

    }

    private void retrieveSeatsSchemeForPlaneTypeIfNeeded(Flight flight) {
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
            System.out.println(flight.getBookedSeatsInPlaneMap());
        }
    }

    private Map<String, String> createReservedSeatsSchemeMap(Map<String, String> savedSeatsSchemeMap) {
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

    private boolean exists(CreateFlightCommand createFlightCommand) {
        return flightRepository.existsByFlightName(createFlightCommand.flightName());
    }
}

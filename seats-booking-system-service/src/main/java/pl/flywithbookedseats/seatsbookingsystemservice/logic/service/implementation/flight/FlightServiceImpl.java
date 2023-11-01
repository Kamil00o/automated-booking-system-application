package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightNotCreatedException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight.CreateFlightMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight.FlightDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.FlightDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.FlightRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.SeatsSchemeModelRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.FlightService;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightConstImpl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightConstImpl.class);

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
        Flight savedFlight = flightRepository.findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));

        if (!(exists(updateFlightCommand) || existsByFlightServiceId(updateFlightCommand))) {
            savedFlight.setFlightName(updateFlightCommand.flightName());
            savedFlight.setPlaneTypeName(updateFlightCommand.planeTypeName());
            savedFlight.setFlightServiceId(updateFlightCommand.flightServiceId());
            if (!updateFlightCommand.bookedSeatsInPlaneMap().isEmpty()) {
                savedFlight.setBookedSeatsInPlaneMap(updateFlightCommand.bookedSeatsInPlaneMap());
            }
            flightRepository.saveAndFlush(savedFlight);
            logger.info(FLIGHT_UPDATED.formatted(savedFlight.getFlightName()));
            return flightDtoMapper.apply(savedFlight);
        } else {
            logger.warn(FLIGHT_NOT_UPDATED.formatted(flightName));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(updateFlightCommand.flightName()));
        }
    }

    @Transactional
    @Override
    public FlightDto updateFlightByFlightServiceId(UpdateFlightCommand updateFlightCommand, Long flightServiceId) {
        Flight savedFlight = flightRepository.findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));

        if (!(exists(updateFlightCommand) || existsByFlightServiceId(updateFlightCommand))) {
            savedFlight.setFlightName(updateFlightCommand.flightName());
            savedFlight.setPlaneTypeName(updateFlightCommand.planeTypeName());
            savedFlight.setFlightServiceId(updateFlightCommand.flightServiceId());
            if (!updateFlightCommand.bookedSeatsInPlaneMap().isEmpty()) {
                savedFlight.setBookedSeatsInPlaneMap(updateFlightCommand.bookedSeatsInPlaneMap());
            }
            flightRepository.saveAndFlush(savedFlight);
            logger.info(FLIGHT_UPDATED.formatted(savedFlight.getFlightName()));
            return flightDtoMapper.apply(savedFlight);
        } else {
            logger.warn(FLIGHT_NOT_UPDATED.formatted(flightServiceId));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(updateFlightCommand.flightName()));
        }
    }

    @Transactional
    @Override
    public List<FlightDto> retrieveAllFlightsFromDb() {
        List<Flight> savedFlightList = flightRepository.findAll();
        if (!savedFlightList.isEmpty()) {
            List<FlightDto> savedFlightDtoList = new ArrayList<>();
            savedFlightList.forEach(flight -> savedFlightDtoList.add(flightDtoMapper.apply(flight)));
            return savedFlightDtoList;
        } else {
            logger.warn(FLIGHTS_NOT_RETRIEVED);
            throw new FlightDatabaseIsEmptyException(FLIGHT_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    @Override
    public FlightDto retrieveFlightByFlightName(String flightName) {
        Flight savedFlight = flightRepository.findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));
        return flightDtoMapper.apply(savedFlight);
    }

    @Override
    public FlightDto retrieveFlightByFlightServiceId(Long flightServiceId) {
        Flight savedFlight = flightRepository.findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));
        return flightDtoMapper.apply(savedFlight);
    }

    @Transactional
    @Override
    public void deleteAllFlights() {
        flightRepository.deleteAll();
        logger.info(FLIGHT_REMOVED_ALL);
    }

    @Transactional
    @Override
    public void deleteFlightByFlightName(String flightName) {
        Flight savedFlight = flightRepository.findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));
        flightRepository.delete(savedFlight);
        logger.info(FLIGHT_REMOVED_NAME.formatted(flightName));
    }

    @Transactional
    @Override
    public void deleteFlightByFlyServiceId(Long flightServiceId) {
        Flight savedFlight = flightRepository.findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));
        flightRepository.delete(savedFlight);
        logger.info(FLIGHT_REMOVED_SERVICE_ID.formatted(flightServiceId));
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

    private boolean exists(UpdateFlightCommand updateFlightCommand) {
        return flightRepository.existsByFlightName(updateFlightCommand.flightName());
    }

    private boolean existsByFlightServiceId(UpdateFlightCommand updateFlightCommand) {
        return flightRepository.existsByFlightServiceId(updateFlightCommand.flightServiceId());
    }
}
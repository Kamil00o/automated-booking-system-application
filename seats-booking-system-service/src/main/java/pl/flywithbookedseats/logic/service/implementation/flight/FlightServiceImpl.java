package pl.flywithbookedseats.logic.service.implementation.flight;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.logic.mapper.flight.FlightDtoMapper;
import pl.flywithbookedseats.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.logic.model.dto.FlightDto;
import pl.flywithbookedseats.logic.repository.FlightRepository;
import pl.flywithbookedseats.logic.service.FlightService;

import static pl.flywithbookedseats.logic.service.implementation.flight.FlightConstImpl.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {

    private static final Logger logger = LoggerFactory.getLogger(FlightConstImpl.class);

    private final FlightRepository flightRepository;
    private final FlightDtoMapper flightDtoMapper;
    private final FlightBusinessLogic flightBL;

    @Transactional
    @Override
    public FlightDto createNewFlight(CreateFlightCommand createFlightCommand) {
        if (!flightBL.exists(createFlightCommand)) {
            return flightDtoMapper.apply(flightBL.generateNewFlight(createFlightCommand));
        } else {
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(createFlightCommand.flightName()));
        }
    }

    @Transactional
    @Override
    public FlightDto updateFlightByFlightName(UpdateFlightCommand updateFlightCommand, String flightName) {
        return flightDtoMapper.apply(flightBL
                .updateSpecigiedFlight(updateFlightCommand, flightBL.retrieveFlightEntityFromDb(flightName)));
    }

    @Transactional
    @Override
    public FlightDto updateFlightByFlightServiceId(UpdateFlightCommand updateFlightCommand, Long flightServiceId) {
        return flightDtoMapper.apply(flightBL
                .updateSpecigiedFlight(updateFlightCommand, flightBL.retrieveFlightEntityFromDb(flightServiceId)));
    }

    @Transactional
    @Override
    public List<FlightDto> retrieveAllFlightsFromDb() {
        return flightBL.convertIntoListFlightDto(flightRepository.findAll());
    }

    @Override
    public FlightDto retrieveFlightByFlightName(String flightName) {
        return flightDtoMapper.apply(flightBL.retrieveFlightEntityFromDb(flightName));
    }

    @Override
    public FlightDto retrieveFlightByFlightServiceId(Long flightServiceId) {
        return flightDtoMapper.apply(flightBL.retrieveFlightEntityFromDb(flightServiceId));
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
        flightRepository.delete(flightBL.retrieveFlightEntityFromDb(flightName));
        logger.info(FLIGHT_REMOVED_NAME.formatted(flightName));
    }

    @Transactional
    @Override
    public void deleteFlightByFlyServiceId(Long flightServiceId) {
        flightRepository.delete(flightBL.retrieveFlightEntityFromDb(flightServiceId));
        logger.info(FLIGHT_REMOVED_SERVICE_ID.formatted(flightServiceId));
    }

    @Transactional
    public String testBookSeatInFlightSeatsScheme() {
        return flightBL.bookSeatInFlightSeatsScheme("FR2001", "Economy Class", 15L,
                true, LocalDate.of(1998, 7, 29));
    }
}

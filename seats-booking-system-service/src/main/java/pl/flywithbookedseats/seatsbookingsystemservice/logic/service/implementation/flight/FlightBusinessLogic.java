package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.*;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight.CreateFlightMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.flight.FlightDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.CreateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight.UpdateFlightCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Flight;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.FlightDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.FlightRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.SeatsSchemeModelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightConstImpl.*;
import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;

@AllArgsConstructor
@Component
public class FlightBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(FlightBusinessLogic.class);

    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final SeatsSchemeModelRepository seatsSchemeModelRepository;
    private final CreateFlightMapper createFlightMapper;
    private final FlightDtoMapper flightDtoMapper;

    public Flight generateNewFlight(CreateFlightCommand createFlightCommand) {
        Flight newFlight = createFlightMapper.apply(createFlightCommand);
        retrieveSeatsSchemeForPlaneTypeIfNeeded(newFlight);
        flightRepository.save(newFlight);
        return newFlight;
    }

    public Flight updateSpecigiedFlight(UpdateFlightCommand updateFlightCommand, Flight flightToUpdate) {
        if (!(exists(updateFlightCommand) || existsByFlightServiceId(updateFlightCommand))) {
            flightToUpdate.setFlightName(updateFlightCommand.flightName());
            flightToUpdate.setPlaneTypeName(updateFlightCommand.planeTypeName());
            flightToUpdate.setFlightServiceId(updateFlightCommand.flightServiceId());
            setBookedSeatsInPlaneMapIfPossible(updateFlightCommand.bookedSeatsInPlaneMap(), flightToUpdate);
            flightRepository.saveAndFlush(flightToUpdate);
            logger.info(FLIGHT_UPDATED.formatted(flightToUpdate.getFlightName()));
            return flightToUpdate;
        } else {
            logger.warn(FLIGHT_NOT_UPDATED.formatted(flightToUpdate.getFlightServiceId()));
            throw new FlightAlreadyExistsException(FLIGHT_ALREADY_EXISTS_FLIGHT_NAME
                    .formatted(updateFlightCommand.flightName()));
        }
    }

    public List<FlightDto> convertIntoListFlightDto(List<Flight> flightList) {
        if (!flightList.isEmpty()) {
            List<FlightDto> savedFlightDtoList = new ArrayList<>();
            flightList.forEach(flight -> savedFlightDtoList.add(flightDtoMapper.apply(flight)));
            return savedFlightDtoList;
        } else {
            logger.warn(FLIGHTS_NOT_RETRIEVED);
            throw new FlightDatabaseIsEmptyException(FLIGHT_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    public Flight retrieveFlightEntityFromDb(String flightName) {
        return flightRepository.findByFlightName(flightName)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName)));
    }

    public Flight retrieveFlightEntityFromDb(Long flightServiceId) {
        return flightRepository.findByFlightServiceId(flightServiceId)
                .orElseThrow(() -> new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID
                        .formatted(flightServiceId)));
    }

    public Passenger retrievePassengerEntityFromDb(Long passengerId) {
        return passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_ID.formatted(passengerId)));
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

    private void retrieveSeatsSchemeForPlaneTypeIfNeeded(Flight flight) {
        Map<String, String> savedSeatsSchemeMap;
        Map<String, Long> generatedBookedSeatsInPlaneMap;
        String planeTypeName = flight.getPlaneTypeName();
        if (flight.getBookedSeatsInPlaneMap() == null) {
            SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository
                    .findByPlaneModelName(planeTypeName)
                    .orElseThrow(() -> new FlightNotCreatedException(SEATS_SCHEME_NOT_FOUND_FLIGHT_NOT_CREATED_EXCEPTION
                            .formatted(planeTypeName, flight.getFlightName())));
            savedSeatsSchemeMap = new TreeMap<>(savedSeatsSchemeModel.getSeatsSchemeMap());
            generatedBookedSeatsInPlaneMap = createReservedSeatsSchemeMap(savedSeatsSchemeMap);
            flight.setBookedSeatsInPlaneMap(generatedBookedSeatsInPlaneMap);
            System.out.println(flight);
        }
    }

    private Map<String, Long> createReservedSeatsSchemeMap(Map<String, String> savedSeatsSchemeMap) {
        Map<String, Long> localBookedSeatsInPlaneMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : savedSeatsSchemeMap.entrySet()) {
            StringBuilder fullSeatName = new StringBuilder();
            fullSeatName.append(entry.getValue())
                    .append(" ")
                    .append(entry.getKey());
            localBookedSeatsInPlaneMap.put(fullSeatName.toString(), Long.valueOf(0));
            fullSeatName.delete(0, fullSeatName.length());
        }

        return localBookedSeatsInPlaneMap;
    }

    private void setBookedSeatsInPlaneMapIfPossible(Map<String, Long> bookedSeatsInPlaneMapToSet
            , Flight flightToUpdate) {
        if (bookedSeatsInPlaneMapToSet != null) {
            if (!bookedSeatsInPlaneMapToSet.isEmpty()) {
                flightToUpdate.setBookedSeatsInPlaneMap(bookedSeatsInPlaneMapToSet);
            }
        }
    }
}

package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.PassengerService;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final PassengerRepository passengerRepository;
    private final PassengerDtoMapper passengerDtoMapper;
    private final PassengerBusinessLogic passengerBL;

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        if (!passengerBL.exists(createPassengerCommand)) {
            return passengerDtoMapper.apply(passengerBL.generateNewPassenger(createPassengerCommand));
        } else {
            logger.warn(PASSENGER_NOT_CREATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL
                    .formatted(createPassengerCommand.email()));
        }
    }

    @Transactional
    @Override
    public PassengerDto updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand, String email) {
        Passenger savedPassenger = passengerBL.retrievePassengerEntityFromDb(email);
        if (passengerBL.exists(email)) {
            return passengerDtoMapper.apply(passengerBL
                    .updateSpecifiedPassenger(updatePassengerCommand, savedPassenger, false));
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new FlightAlreadyExistsException(PASSENGER_NOT_FOUND_EMAIL.formatted(email));
        }
    }

    @Override
    public PassengerDto retrievePassengerByEmail(String email) {
        return passengerDtoMapper.apply(passengerBL.retrievePassengerEntityFromDb(email));
    }

    @Override
    public List<PassengerDto> retrieveAllPassengers() {
        return passengerBL.convertIntoListPassengerDto(passengerRepository.findAll());
    }

    @Transactional
    @Override
    public void deleteAllPassengers() {
        passengerRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deletePassengerByEmail(String email) {
        passengerRepository.delete(passengerBL.retrievePassengerEntityFromDb(email));
    }
}

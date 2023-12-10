package pl.flywithbookedseats.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.kafka.RequestType;
import pl.flywithbookedseats.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.logic.exceptions.PassengerAlreadyExistsException;
import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.logic.repository.PassengerRepository;
import pl.flywithbookedseats.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.logic.service.PassengerService;

import static pl.flywithbookedseats.logic.service.implementation.passenger.PassengerConstsImpl.*;

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
            PassengerDto createdPassengerDto = passengerDtoMapper
                    .apply(passengerBL.generateNewPassenger(createPassengerCommand));
            passengerBL.sendUpdatedPassengerEvent(createdPassengerDto);
            return createdPassengerDto;
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
            PassengerDto updatedPassengerDto = passengerDtoMapper.apply(passengerBL
                    .updateSpecifiedPassenger(updatePassengerCommand, savedPassenger, false));
            passengerBL.sendPassengerDtoAsync(RequestType.UPDATE, updatedPassengerDto);
            return updatedPassengerDto;
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
    public void deletePassengerById(Long id) {
        passengerBL.deletePassengerById(id);
    }

    @Transactional
    @Override
    public void deletePassengerByEmail(String email) {
        passengerBL.deletePassengerByEmail(email);
    }
}

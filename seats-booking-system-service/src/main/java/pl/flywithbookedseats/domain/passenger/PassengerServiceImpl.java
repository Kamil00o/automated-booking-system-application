package pl.flywithbookedseats.domain.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.api.passeger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passeger.UpdatePassengerCommand;
import pl.flywithbookedseats.external.storage.passenger.Passenger;
import pl.flywithbookedseats.api.passeger.PassengerDto;
import pl.flywithbookedseats.external.storage.passenger.PassengerRepository;
import pl.flywithbookedseats.api.passeger.PassengerDtoMapper;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.*;

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
            passengerBL.sendRequestedPassengerEvent(createdPassengerDto);
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
            passengerBL.sendUpdatedPassengerEvent(updatedPassengerDto);
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

    public Passenger retrievePassengerById(Long id) {
        return passengerRepository.findById(id).orElse(null);
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

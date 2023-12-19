package pl.flywithbookedseats.domain.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.passeger.*;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepository;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.*;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PassengerService1Impl implements PassengerService1 {

    private static final Logger logger = LoggerFactory.getLogger(PassengerService1Impl.class);

    private final JpaPassengerRepository jpaPassengerRepository;
    private final PassengerDtoMapper1 passengerDtoMapper1;
    private final PassengerBusinessLogic passengerBL;

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        if (!passengerBL.exists(createPassengerCommand)) {
            PassengerDto createdPassengerDto = passengerDtoMapper1
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
        PassengerEntity savedPassengerEntity = passengerBL.retrievePassengerEntityFromDb(email);
        if (passengerBL.exists(email)) {
            PassengerDto updatedPassengerDto = passengerDtoMapper1.apply(passengerBL
                    .updateSpecifiedPassenger(updatePassengerCommand, savedPassengerEntity, false));
            passengerBL.sendUpdatedPassengerEvent(updatedPassengerDto);
            return updatedPassengerDto;
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new FlightAlreadyExistsException(PASSENGER_NOT_FOUND_EMAIL.formatted(email));
        }
    }

    @Override
    public PassengerDto retrievePassengerByEmail(String email) {
        return passengerDtoMapper1.apply(passengerBL.retrievePassengerEntityFromDb(email));
    }

    public PassengerEntity retrievePassengerById(Long id) {
        return jpaPassengerRepository.findById(id).orElse(null);
    }

    @Override
    public List<PassengerDto> retrieveAllPassengers() {
        return passengerBL.convertIntoListPassengerDto(jpaPassengerRepository.findAll());
    }

    @Transactional
    @Override
    public void deleteAllPassengers() {
        jpaPassengerRepository.deleteAll();
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
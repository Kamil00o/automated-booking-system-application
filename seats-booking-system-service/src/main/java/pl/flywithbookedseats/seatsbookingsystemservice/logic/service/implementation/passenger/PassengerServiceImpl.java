package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.PassengerService;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PassengerServiceImpl implements PassengerService {

    private static final Logger logger = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final PassengerRepository passengerRepository;
    //Adding ReservationServiceImpl instance causes circular references with ReservationServiceImpl Bean:
    //private final ReservationServiceImpl reservationService;
    private final PassengerDtoMapper passengerDtoMapper;

    @Transactional
    @Override
    public PassengerDto createNewPassenger(CreatePassengerCommand createPassengerCommand) {
        if (exists(createPassengerCommand)) {
        //TODO:method will be finished after redesigning!
        }
        return null;
    }

    @Transactional
    @Override
    public PassengerDto updatePassengerByEmail(UpdatePassengerCommand updatePassengerCommand, String email) {
        Passenger savedPassenger = retrievePassengerEntityFromDb(updatePassengerCommand.email());
        if (exists(email)) {
            return passengerDtoMapper.apply(updateSpecifiedPassenger(updatePassengerCommand, savedPassenger));
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new FlightAlreadyExistsException(PASSENGER_NOT_FOUND_EMAIL.formatted(email));
        }
    }

    @Override
    public PassengerDto retrievePassengerByEmail(String email) {
        return passengerDtoMapper.apply(retrievePassengerEntityFromDb(email));
    }

    @Override
    public List<PassengerDto> retrieveAllPassengers() {
        List<Passenger> savedPassengerList = passengerRepository.findAll();
        return convertIntoListPassengerDto(savedPassengerList);
    }

    @Transactional
    @Override
    public void deleteAllPassengers() {
        passengerRepository.deleteAll();
    }

    @Transactional
    @Override
    public void deletePassengerByEmail(String email) {
        passengerRepository.delete(retrievePassengerEntityFromDb(email));
    }

    private List<Reservation> parseReservationIdToEntity(CreatePassengerCommand createPassengerCommand) {
        List<Reservation> parsedReservationList = new ArrayList<>();
        List<Long> reservationIdList = createPassengerCommand.reservationsIdList();
        /*if (!reservationIdList.isEmpty()) {
            reservationIdList.forEach(id -> {
                reservationService.retrieveReservationById(id);
                parsedReservationList.add();
            });
        }*/
        return null;
    }

    private List<PassengerDto> convertIntoListPassengerDto(List<Passenger> localSavedPassengerList) {
        if (!localSavedPassengerList.isEmpty()) {
            List<PassengerDto> savedPassengerDtoList = new ArrayList<>();
            localSavedPassengerList.forEach(passenger -> savedPassengerDtoList
                    .add(passengerDtoMapper.apply(passenger)));
            return savedPassengerDtoList;
        } else {
            logger.warn(PASSENGERS_NOT_RETRIEVED);
            throw new PassengerDatabaseIsEmptyException(PASSENGER_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    private Passenger updateSpecifiedPassenger(UpdatePassengerCommand updatePassengerCommand,
                                               Passenger savedPassenger) {
        String email = updatePassengerCommand.email();
        if (exists(updatePassengerCommand)) {
            if (!updatePassengerCommand.reservationsIdList().isEmpty()) {
                //TODO: Will be finished, when ReservationService's method retrieveReservationEntityFromDb()
                // will be extracted to separated method to enable using it here
            }
            savedPassenger.setBirthDate(updatePassengerCommand.birthDate());
            savedPassenger.setName(updatePassengerCommand.name());
            savedPassenger.setSurname(updatePassengerCommand.surname());
            savedPassenger.setEmail(email);
            savedPassenger.setDisability(updatePassengerCommand.disability());
            passengerRepository.saveAndFlush(savedPassenger);
            return savedPassenger;
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL.formatted(email));
        }
    }

    public Passenger retrievePassengerEntityFromDb(String email) {
        return passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));
    }

    public boolean exists(String email) {
        return passengerRepository.existsByEmail(email);
    }

    private boolean exists(CreatePassengerCommand createPassengerCommand) {
        return passengerRepository.existsByEmail(createPassengerCommand.email());
    }

    private boolean exists(UpdatePassengerCommand updatePassengerCommand) {
        return passengerRepository.existsByEmail(updatePassengerCommand.email());
    }
}

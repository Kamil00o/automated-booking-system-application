package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.CreatePassengerMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationBusinessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;

@AllArgsConstructor
@Component
public class PassengerBusinessLogic {
    private static final Logger logger = LoggerFactory.getLogger(PassengerBusinessLogic.class);

    private final PassengerRepository passengerRepository;
    private final ReservationBusinessLogic reservationBL;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreatePassengerMapper createPassengerMapper;

    public Passenger generateNewPassenger(CreatePassengerCommand createPassengerCommand) {
        Passenger newPassenger = createPassengerMapper.apply(createPassengerCommand);
        List<Reservation> reservationsToAddList = parseReservationIdToReservationEntity(createPassengerCommand
                .reservationsIdList());
        reservationsToAddList.forEach(reservation -> addReservationEntityToPassengerEntity(newPassenger, reservation));
        passengerRepository.save(newPassenger);
        return newPassenger;
    }

    public List<PassengerDto> convertIntoListPassengerDto(List<Passenger> localSavedPassengerList) {
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

    public Passenger updateSpecifiedPassenger(UpdatePassengerCommand updatePassengerCommand,
                                               Passenger savedPassenger) {
        String email = updatePassengerCommand.email();
        if (exists(updatePassengerCommand)) {
            List<Reservation> reservationsToUpdateList = parseReservationIdToReservationEntity(updatePassengerCommand
                    .reservationsIdList());
            if (!reservationsToUpdateList.isEmpty()) {
                reservationsToUpdateList.forEach(reservation -> addReservationEntityToPassengerEntity(savedPassenger, reservation));
                /*for (Long reservationId : updatedReservationsIdList) {
                    addReservationEntityToPassengerEntity(savedPassenger
                            , reservationBL.retrieveReservationEntityFromDb(reservationId));
                }*/
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

    public boolean exists(CreatePassengerCommand createPassengerCommand) {
        return passengerRepository.existsByEmail(createPassengerCommand.email());
    }

    public boolean exists(UpdatePassengerCommand updatePassengerCommand) {
        return passengerRepository.existsByEmail(updatePassengerCommand.email());
    }

    private void addReservationEntityToPassengerEntity(Passenger passengerEntity, Reservation reservationToAdd) {
        List<Reservation> reservationList = passengerEntity.getReservationsList();
        if (reservationList == null) {
            passengerEntity.setReservationsList(Collections.singletonList(reservationToAdd));
        } else {
            reservationList.add(reservationToAdd);
        }
    }

    private List<Reservation> parseReservationIdToReservationEntity(List<Long> reservationIdList) {
        List<Reservation> parsedReservationList = new ArrayList<>();
        if (!reservationIdList.isEmpty()) {
            reservationIdList.forEach(id -> {
                parsedReservationList.add(reservationBL.retrieveReservationEntityFromDb(id));
            });
        }
        return parsedReservationList;
    }
}

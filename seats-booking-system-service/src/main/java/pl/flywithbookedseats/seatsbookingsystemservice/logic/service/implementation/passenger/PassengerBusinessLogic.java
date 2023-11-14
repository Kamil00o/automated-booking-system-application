package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.CreatePassengerMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.DtoPassengerMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.PassengerRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerConstsImpl.*;
import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationConstsImpl.RESERVATION_NOT_FOUND_ID;

@AllArgsConstructor
@EnableFeignClients
@Component
public class PassengerBusinessLogic {
    private static final Logger logger = LoggerFactory.getLogger(PassengerBusinessLogic.class);

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final PassengerDtoMapper passengerDtoMapper;
    private final CreatePassengerMapper createPassengerMapper;
    private final PassengerAccountProxy passengerAccountProxy;
    private final DtoPassengerMapper dtoPassengerMapper;

    public Passenger generateNewPassenger(CreatePassengerCommand createPassengerCommand) {
        Passenger newPassenger = createPassengerMapper.apply(createPassengerCommand);
        if (newPassenger.getPassengerServiceId() == null) {
            newPassenger.setPassengerServiceId(getPassengerServiceId(newPassenger.getEmail()));
        }
        
        List<Reservation> reservationsToAddList = parseReservationIdToReservationEntity(createPassengerCommand
                .reservationsIdList());
        if (reservationsToAddList != null) {
            reservationsToAddList.forEach(reservation -> addReservationEntityToPassengerEntity(newPassenger, reservation));
        }

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
                                              Passenger savedPassenger, boolean doNotSaveInDb) {
        String email = updatePassengerCommand.email();
        if (!exists(updatePassengerCommand, savedPassenger)) {
            List<Reservation> reservationsToUpdateList = parseReservationIdToReservationEntity(updatePassengerCommand
                    .reservationsIdList());
            if (reservationsToUpdateList != null) {
                if (!reservationsToUpdateList.isEmpty()) {
                    reservationsToUpdateList.forEach(reservation ->
                            addReservationEntityToPassengerEntity(savedPassenger, reservation));
                }
            }
            savedPassenger.setBirthDate(updatePassengerCommand.birthDate());
            savedPassenger.setName(updatePassengerCommand.name());
            savedPassenger.setSurname(updatePassengerCommand.surname());
            savedPassenger.setEmail(email);
            savedPassenger.setDisability(updatePassengerCommand.disability());
            savePassengerEntityInDb(doNotSaveInDb, savedPassenger);
            return savedPassenger;
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL.formatted(email));
        }
    }

    public Long getPassengerServiceId(String email) {
        return getPassengerAccountDtoData(email).getId();
    }

    public Passenger getPassengerAccountDtoData(String email) {
        return dtoPassengerMapper.apply(passengerAccountProxy.getPassengerAccountDtoData(email));
    }

    public void deletePassengerById(Long id) {
        passengerRepository.deleteById(id);
    }

    public void deletePassengerByEmail(String email) {
        passengerRepository.deleteByEmail(email);
    }

    public Passenger retrievePassengerEntityFromDb(String email) {
        return passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));
    }

    public void savePassengerEntityInDb(boolean skipSaving, Passenger passengerEntity) {
        if (!skipSaving) {
            passengerRepository.save(passengerEntity);
        }
    }

    public boolean exists(String email) {
        return passengerRepository.existsByEmail(email);
    }

    public boolean exists(CreatePassengerCommand createPassengerCommand) {
        return passengerRepository.existsByEmail(createPassengerCommand.email());
    }

    public boolean exists(UpdatePassengerCommand updatePassengerCommand, Passenger existingPassenger) {
        String email = updatePassengerCommand.email();
        if (passengerRepository.existsByEmail(email)) {
            if (Objects.equals(retrievePassengerEntityFromDb(email).getId(), existingPassenger.getId())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
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
        if (reservationIdList != null) {
            if (!reservationIdList.isEmpty()) {
                reservationIdList.forEach(id -> parsedReservationList.add(retrieveReservationEntityFromDb(id)));
            }

            return parsedReservationList;
        } else {
            logger.debug("Passed reservationIdList is null!");
            return null;
        }
    }

    //duplicated - ReservationBL//
    //What with the case, where some bad ID will be passed ? IF throwing exception in this case will be ok ?
    //Probably not, because we will interrupt code execution during iteration through the list & we will not
    //retrieve the rest of reservations entities for correct IDs, which left...
    private Reservation retrieveReservationEntityFromDb(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));
    }
}

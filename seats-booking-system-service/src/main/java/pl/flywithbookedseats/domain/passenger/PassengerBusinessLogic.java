package pl.flywithbookedseats.domain.passenger;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.external.message.passenger.BookingServiceProducer;
import pl.flywithbookedseats.external.message.passenger.EventsFactory;
import pl.flywithbookedseats.domain.reservation.ReservationNotFoundException;
import pl.flywithbookedseats.api.passenger.CreatePassengerMapper;
import pl.flywithbookedseats.api.passenger.DtoPassengerMapper;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper1;
import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;
import pl.flywithbookedseats.api.passenger.PassengerDto;
import pl.flywithbookedseats.external.storage.passenger.JpaPassengerRepository;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static pl.flywithbookedseats.domain.passenger.PassengerConstsImpl.*;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_NOT_FOUND_ID;

@AllArgsConstructor
//@EnableFeignClients
@Component
public class PassengerBusinessLogic {
    private static final Logger logger = LoggerFactory.getLogger(PassengerBusinessLogic.class);

    private final JpaPassengerRepository jpaPassengerRepository;
    private final JpaReservationRepository jpaReservationRepository;
    private final PassengerDtoMapper1 passengerDtoMapper1;
    private final CreatePassengerMapper createPassengerMapper;
    //private final FeignPassengerService feignPassengerService;
    private final DtoPassengerMapper dtoPassengerMapper;
    private final BookingServiceProducer bookingServiceProducer;

    public PassengerEntity generateNewPassenger(CreatePassengerCommand createPassengerCommand) {
        PassengerEntity newPassengerEntity = createPassengerMapper.apply(createPassengerCommand);
        try {
            if (newPassengerEntity.getPassengerServiceId() == null) {
                newPassengerEntity.setPassengerServiceId(getPassengerServiceId(newPassengerEntity.getEmail()));
            }
        } catch (Exception exception) {
            logger.info("passengerServiceId not retrieved from the passengerEntity service");
        }
        
        List<ReservationEntity> reservationsToAddList = parseReservationIdToReservationEntity(createPassengerCommand
                .reservationsIdList());
        if (reservationsToAddList != null) {
            reservationsToAddList.forEach(reservation -> addReservationEntityToPassengerEntity(newPassengerEntity, reservation));
        }

        jpaPassengerRepository.save(newPassengerEntity);
        return newPassengerEntity;
    }

    public List<PassengerDto> convertIntoListPassengerDto(List<PassengerEntity> localSavedPassengerListEntity) {
        if (!localSavedPassengerListEntity.isEmpty()) {
            List<PassengerDto> savedPassengerDtoList = new ArrayList<>();
            localSavedPassengerListEntity.forEach(passenger -> savedPassengerDtoList
                    .add(passengerDtoMapper1.apply(passenger)));
            return savedPassengerDtoList;
        } else {
            logger.warn(PASSENGERS_NOT_RETRIEVED);
            throw new PassengerDatabaseIsEmptyException(PASSENGER_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    public PassengerEntity updateSpecifiedPassenger(UpdatePassengerCommand updatePassengerCommand,
                                                    PassengerEntity savedPassengerEntity, boolean doNotSaveInDb) {
        String email = updatePassengerCommand.email();
        if (!exists(updatePassengerCommand, savedPassengerEntity)) {
            List<ReservationEntity> reservationsToUpdateList = parseReservationIdToReservationEntity(updatePassengerCommand
                    .reservationsIdList());
            if (reservationsToUpdateList != null) {
                if (!reservationsToUpdateList.isEmpty()) {
                    reservationsToUpdateList.forEach(reservation ->
                            addReservationEntityToPassengerEntity(savedPassengerEntity, reservation));
                }
            }
            savedPassengerEntity.setBirthDate(updatePassengerCommand.birthDate());
            savedPassengerEntity.setName(updatePassengerCommand.name());
            savedPassengerEntity.setSurname(updatePassengerCommand.surname());
            savedPassengerEntity.setEmail(email);
            savedPassengerEntity.setDisability(updatePassengerCommand.disability());
            savePassengerEntityInDb(doNotSaveInDb, savedPassengerEntity);
            return savedPassengerEntity;
        } else {
            logger.warn(PASSENGER_NOT_UPDATED);
            throw new PassengerAlreadyExistsException(PASSENGER_ALREADY_EXISTS_EMAIL.formatted(email));
        }
    }

    public Long getPassengerServiceId(String email) {
        return getPassengerAccountDtoData(email).getPassengerServiceId();
    }

    public PassengerEntity getPassengerAccountDtoData(String email) {
        //TODO: After new DTO for feign implementation it has been commented:
        //return dtoPassengerMapper.apply(feignPassengerService.getPassengerAccountDtoData(email));
        return null;
    }

    public void deletePassengerById(Long id) {
        jpaPassengerRepository.deleteById(id);
    }

    public void deletePassengerByEmail(String email) {
        jpaPassengerRepository.deleteByEmail(email);
    }

    public PassengerEntity retrievePassengerEntityFromDb(String email) {
        return jpaPassengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));
    }

    public void savePassengerEntityInDb(boolean skipSaving, PassengerEntity passengerEntity) {
        if (!skipSaving) {
            jpaPassengerRepository.save(passengerEntity);
        }
    }

    public boolean exists(String email) {
        return jpaPassengerRepository.existsByEmail(email);
    }

    public boolean exists(CreatePassengerCommand createPassengerCommand) {
        return jpaPassengerRepository.existsByEmail(createPassengerCommand.email());
    }

    public boolean exists(UpdatePassengerCommand updatePassengerCommand, PassengerEntity existingPassengerEntity) {
        String email = updatePassengerCommand.email();
        if (jpaPassengerRepository.existsByEmail(email)) {
            if (Objects.equals(retrievePassengerEntityFromDb(email).getId(), existingPassengerEntity.getId())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void sendRequestedPassengerEvent(PassengerDto passengerDto) {
        bookingServiceProducer.sendRequestedPassengerEvent(EventsFactory.createRequestedPassengerEvent(passengerDto));
    }

    public void sendUpdatedPassengerEvent(PassengerDto passengerDto) {
        bookingServiceProducer.sendUpdatedPassengerEvent(EventsFactory.createUpdatedPassengerEvent(passengerDto));
    }

    private void addReservationEntityToPassengerEntity(PassengerEntity passengerEntity, ReservationEntity reservationEntityToAdd) {
        List<ReservationEntity> reservationEntityList = passengerEntity.getReservationsList();
        if (reservationEntityList == null) {
            passengerEntity.setReservationsList(Collections.singletonList(reservationEntityToAdd));
        } else {
            reservationEntityList.add(reservationEntityToAdd);
        }
    }

    public List<ReservationEntity> parseReservationIdToReservationEntity(List<Long> reservationIdList) {
        List<ReservationEntity> parsedReservationListEntity = new ArrayList<>();
        if (reservationIdList != null) {
            if (!reservationIdList.isEmpty()) {
                reservationIdList.forEach(id -> parsedReservationListEntity.add(retrieveReservationEntityFromDb(id)));
            }

            return parsedReservationListEntity;
        } else {
            logger.debug("Passed reservationIdList is null!");
            return null;
        }
    }

    //duplicated - ReservationBL//
    //What with the case, where some bad ID will be passed ? IF throwing exception in this case will be ok ?
    //Probably not, because we will interrupt code execution during iteration through the list & we will not
    //retrieve the rest of reservations entities for correct IDs, which left...
    private ReservationEntity retrieveReservationEntityFromDb(Long id) {
        return jpaReservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));
    }
}

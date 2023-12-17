package pl.flywithbookedseats.domain.reservation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.logic.exceptions.PassengerNotFoundException;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.logic.repository.PassengerRepository;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepository;
import pl.flywithbookedseats.api.reservation.CreateReservationMapper;
import pl.flywithbookedseats.api.reservation.ReservationDtoMapper1;

import java.util.ArrayList;
import java.util.List;

import static pl.flywithbookedseats.logic.service.implementation.passenger.PassengerConstsImpl.PASSENGER_NOT_FOUND_EMAIL;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;

@AllArgsConstructor
@Component
public class ReservationBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(ReservationBusinessLogic.class);

    private final JpaReservationRepository jpaReservationRepository;
    private final PassengerRepository passengerRepository;
    private final CreateReservationMapper createReservationMapper;
    private final ReservationDtoMapper1 reservationDtoMapper1;

    public List<ReservationDto> convertIntoListReservationDto(List<ReservationEntity> localSavedReservationListEntity) {
        if (!localSavedReservationListEntity.isEmpty()) {
            List<ReservationDto> savedReservationDtoList = new ArrayList<>();
            localSavedReservationListEntity.forEach(reservation -> savedReservationDtoList
                    .add(reservationDtoMapper1.apply(reservation)));
            return savedReservationDtoList;
        } else {
            logger.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationDatabaseIsEmptyException(RESERVATION_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    public ReservationEntity generateNewReservation(CreateReservationCommand createReservationCommand) {
        ReservationEntity newReservationEntity = createReservationMapper.apply(createReservationCommand);
        String passengerEmail = newReservationEntity.getPassengerEmail();
        if (passengerExists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, newReservationEntity);
            jpaReservationRepository.save(newReservationEntity);
            logger.info(RESERVATION_ADDED_PASSENGER);
        } else {
            jpaReservationRepository.save(newReservationEntity);
            logger.info(RESERVATION_ADDED_NO_PASSENGER);
        }

        return newReservationEntity;
    }

    public ReservationEntity updateSpecifiedReservation(UpdateReservationCommand updateReservationCommand,
                                                        ReservationEntity savedReservationEntity) {
        String passengerEmail = updateReservationCommand.passengerEmail();
        savedReservationEntity.setSeatNumber(updateReservationCommand.seatNumber());
        savedReservationEntity.setSeatTypeClass(updateReservationCommand.seatTypeClass());
        savedReservationEntity.setPassengerEmail(passengerEmail);
        if (passengerExists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, savedReservationEntity);
        }
        jpaReservationRepository.saveAndFlush(savedReservationEntity);

        return savedReservationEntity;
    }

    public void deleteReservationById(Long id) {
        jpaReservationRepository.deleteById(id);
    }

    public ReservationEntity retrieveReservationEntityFromDb(Long id) {
        return jpaReservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));
    }
    public boolean exists(UpdateReservationCommand updateReservationCommand) {
        String seatNumber = updateReservationCommand.seatNumber();
        if (jpaReservationRepository.existsBySeatNumber(seatNumber)) {
            if (!retrieveReservationEntityFromDb(seatNumber).getPassengerEmail()
                    .equals(updateReservationCommand.passengerEmail())) {
                return true;
            }
        }
        return false;
    }

    public boolean exists(CreateReservationCommand createReservationCommand) {
        return jpaReservationRepository.existsBySeatNumber(createReservationCommand.seatNumber());
    }

    private void setPassengerDataToReservation(String passengerEmail, ReservationEntity reservationEntity) {
        Passenger savedPassenger = retrievePassengerEntityFromDb(passengerEmail);
        reservationEntity.setPassenger(savedPassenger);
    }

    //duplicated - PassengerBL//
    private Passenger retrievePassengerEntityFromDb(String email) {
        return passengerRepository.findByEmail(email)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND_EMAIL.formatted(email)));
    }

    private ReservationEntity retrieveReservationEntityFromDb(String seatNumber) {
        return jpaReservationRepository.findBySeatNumber(seatNumber)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_SEAT_NUMBER
                        .formatted(seatNumber)));
    }

    //duplicated - PassengerBL//
    private boolean passengerExists(String email) {
        return passengerRepository.existsByEmail(email);
    }
}

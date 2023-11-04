package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationDatabaseIsEmptyException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.ReservationNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.CreateReservationMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.ReservationRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationConstsImpl.*;

@AllArgsConstructor
@Component
public class ReservationBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(ReservationBusinessLogic.class);

    private final ReservationRepository reservationRepository;
    private final CreateReservationMapper createReservationMapper;
    private final ReservationDtoMapper reservationDtoMapper;
    private final PassengerBusinessLogic passengerBL;


    public List<ReservationDto> convertIntoListReservationDto(List<Reservation> localSavedReservationList) {
        if (!localSavedReservationList.isEmpty()) {
            List<ReservationDto> savedReservationDtoList = new ArrayList<>();
            localSavedReservationList.forEach(reservation -> savedReservationDtoList
                    .add(reservationDtoMapper.apply(reservation)));
            return savedReservationDtoList;
        } else {
            logger.warn(RESERVATIONS_NOT_RETRIEVED);
            throw new ReservationDatabaseIsEmptyException(RESERVATION_DATABASE_IS_EMPTY_EXCEPTION);
        }
    }

    public Reservation generateNewReservation(CreateReservationCommand createReservationCommand) {
        Reservation newReservation = createReservationMapper.apply(createReservationCommand);
        String passengerEmail = newReservation.getPassengerEmail();
        if (passengerBL.exists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, newReservation);
            reservationRepository.save(newReservation);
            logger.info(RESERVATION_ADDED_PASSENGER);
        } else {
            reservationRepository.save(newReservation);
            logger.info(RESERVATION_ADDED_NO_PASSENGER);
        }

        return newReservation;
    }

    public Reservation updateSpecifiedReservation(UpdateReservationCommand updateReservationCommand,
                                                   Reservation savedReservation, String passengerEmail) {
        savedReservation.setSeatNumber(updateReservationCommand.seatNumber());
        savedReservation.setSeatTypeClass(updateReservationCommand.seatTypeClass());
        savedReservation.setPassengerEmail(passengerEmail);
        if (passengerBL.exists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, savedReservation);
        }
        reservationRepository.saveAndFlush(savedReservation);

        return savedReservation;
    }

    public Reservation retrieveReservationEntityFromDb(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(RESERVATION_NOT_FOUND_ID.formatted(id)));
    }
    public boolean exists(UpdateReservationCommand updateReservationCommand) {
        return reservationRepository.existsBySeatNumber(updateReservationCommand.seatNumber());
    }

    public boolean exists(CreateReservationCommand createReservationCommand) {
        return reservationRepository.existsBySeatNumber(createReservationCommand.seatNumber());
    }

    private void setPassengerDataToReservation(String passengerEmail, Reservation reservation) {
        Passenger savedPassenger = passengerBL.retrievePassengerEntityFromDb(passengerEmail);
        reservation.setPassenger(savedPassenger);
    }
}

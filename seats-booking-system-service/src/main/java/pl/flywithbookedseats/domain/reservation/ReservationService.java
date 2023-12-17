package pl.flywithbookedseats.domain.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_ADDED_NO_PASSENGER;

@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final PassengerBusinessLogic passengerService;

    public Reservation addNewReservationToDb(Reservation reservation) {
        if (!exists(reservation)) {
            return generateNewReservation(reservation);
        } else {
            log.warn(RESERVATION_NOT_CREATED);
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(reservation.getSeatNumber()));
        }
    }

    public Reservation generateNewReservation(Reservation reservation) {
        String passengerEmail = reservation.getPassengerEmail();
        if (passengerExists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, reservation);
            repository.save(reservation);
            log.info(RESERVATION_ADDED_PASSENGER);
        } else {
            repository.save(reservation);
            log.info(RESERVATION_ADDED_NO_PASSENGER);
        }

        return reservation;
    }

    /*public boolean exists(Reservation reservation) {
        String seatNumber = reservation.getSeatNumber();
        if (repository.existsBySeatNumber(seatNumber)) {
            if (!retrieveReservationEntityFromDb(seatNumber).getPassengerEmail()
                    .equals(reservation.passengerEmail())) {
                return true;
            }
        }
        return false;
    }*/

    public boolean exists(Reservation reservation) {
        return repository.existsBySeatNumber(reservation.getSeatNumber());
    }

    private void setPassengerDataToReservation(String passengerEmail, Reservation reservation) {
        Passenger savedPassenger = passengerService.retrievePassengerEntityFromDb(passengerEmail);
        reservation.setPassenger(savedPassenger);
    }

    private boolean passengerExists(String email) {
        return passengerService.exists(email);
    }
}

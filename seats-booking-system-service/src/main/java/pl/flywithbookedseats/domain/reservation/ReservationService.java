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

    public Reservation retrieveReservationById(Long id) {
        return repository.findById(id);
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

    public Reservation updateReservationById(Reservation reservation, Long id) {
        Reservation savedReservation = retrieveReservationById(id);
        return updateSpecifiedReservation(reservation, savedReservation);
    }

    private Reservation updateSpecifiedReservation(Reservation reservationUpdateData,
                                                        Reservation reservationToUpdate) {
        if (!exists(reservationUpdateData, reservationToUpdate)) {
            String passengerEmail = reservationUpdateData.getPassengerEmail();
            reservationToUpdate.setSeatNumber(reservationUpdateData.getSeatNumber());
            reservationToUpdate.setSeatTypeClass(reservationUpdateData.getSeatTypeClass());
            reservationToUpdate.setPassengerEmail(passengerEmail);
            if (passengerExists(passengerEmail)) {
                setPassengerDataToReservation(passengerEmail, reservationToUpdate);
            }

            repository.save(reservationToUpdate);

            return reservationToUpdate;
        } else {
            log.warn(RESERVATION_NOT_UPDATED.formatted(reservationToUpdate.getId()));
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(reservationUpdateData.getSeatNumber()));
        }

    }

    public void deleteAllReservations() {
        repository.deleteAll();
    }

    public void deleteReservationById(Long id) {
        repository.deleteById(id);
    }

    public boolean exists(Reservation reservationUpdateData, Reservation reservationToUpdate) {
        String seatNumber = reservationUpdateData.getSeatNumber();
        if (repository.existsBySeatNumber(seatNumber)) {
            return !repository
                    .findBySeatNumber(seatNumber)
                    .getPassengerEmail()
                    .equals(reservationToUpdate.getPassengerEmail());
        }

        return false;
    }

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

package pl.flywithbookedseats.domain.reservation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.flight.FlightAlreadyExistsException;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.*;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_ADDED_NO_PASSENGER;

@Slf4j
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository repository;
    private final PassengerService passengerService;

    public Reservation addNewReservationToDb(Reservation reservation) {
        if (!exists(reservation)) {
            return generateNewReservation(reservation);
        } else {
            log.warn(RESERVATION_NOT_CREATED);
            throw new FlightAlreadyExistsException(RESERVATION_ALREADY_EXISTS_SEAT_NUMBER
                    .formatted(reservation.getSeatNumber()));
        }
    }

    public PageReservation retrieveAllReservations(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Reservation retrieveReservationById(Long id) {
        return repository.findById(id);
    }

    public List<Reservation> retrieveReservationByEmail(String passengerEmail) {
        return repository.findAllByPassengerEmail(passengerEmail);
    }

    public Reservation generateNewReservation(Reservation reservation) {
        String passengerEmail = reservation.getPassengerEmail();
        if (passengerExists(passengerEmail)) {
            setPassengerDataToReservation(passengerEmail, reservation);
            Reservation createdReservation = repository.save(reservation);
            log.info(RESERVATION_ADDED_PASSENGER);
            return createdReservation;
        } else {
            Reservation createdReservation = repository.save(reservation);
            log.info(RESERVATION_ADDED_NO_PASSENGER);
            return createdReservation;
        }
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
                    .equals(reservationToUpdate
                            .getPassengerEmail()) &&
                    repository
                            .findBySeatNumber(seatNumber)
                            .getFlightNumber()
                            .equals(reservationToUpdate
                                    .getFlightNumber());
        }

        return false;
    }

    public List<Reservation> parseReservationIdToReservationEntity(List<Long> reservationIdList) {
        List<Reservation> parsedReservationListEntity = new ArrayList<>();
        if (reservationIdList != null) {
            if (!reservationIdList.isEmpty()) {
                reservationIdList.forEach(id -> parsedReservationListEntity.add(retrieveReservationById(id)));
            }

            return parsedReservationListEntity;
        } else {
            log.debug("Passed reservationIdList is null!");
            return null;
        }
    }

    private boolean exists(Reservation reservation) {
        String seatNumber = reservation.getSeatNumber();
        if (repository.existsBySeatNumber(seatNumber)) {
            List<Reservation> retrievedReservationsList = repository.findByFlightNumber(reservation.getFlightNumber());
            if (!retrievedReservationsList.isEmpty()) {
                return !retrievedReservationsList
                        .stream()
                        .filter(reservationElement -> reservationElement
                                .getSeatNumber()
                                .equals(seatNumber))
                        .collect(Collectors.toList()).isEmpty();
            } else {
                return !retrievedReservationsList.isEmpty();
            }

        }

        return false;
    }

    private void setPassengerDataToReservation(String passengerEmail, Reservation reservation) {
        Passenger savedPassengerEntity = passengerService.retrievePassengerByEmail(passengerEmail);
        reservation.setPassenger(savedPassengerEntity);
    }

    private boolean passengerExists(String email) {
        return passengerService.exists(email);
    }
}

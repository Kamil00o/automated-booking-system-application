package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;

import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@Slf4j
@RequiredArgsConstructor
public class SeatsBookingService {

    private final FlightService flightService;
    private final PassengerService passengerService;
    private final ReservationService reservationService;

    public Reservation bookSeatsInThePlane(BookingEnterData bookingEnterData) {
        Passenger newPassenger;
        Reservation newReservation;
        String bookedSeat;
        String flightName = bookingEnterData.getFlightName();
        ExistingPassenger notExistingPassenger = new ExistingPassenger(false);


        if (flightService.exists(flightName)) {
            newPassenger = createPassenger(bookingEnterData, notExistingPassenger);
            bookedSeat = flightService.bookSeatInFlightSeatsScheme(flightName,
                    bookingEnterData.getSeatClassType(),
                    newPassenger.getId(),
                    bookingEnterData.isDisability(),
                    bookingEnterData.getPassengerBirthDate());
            newReservation = reservationService.generateNewReservation(parseReservationData(bookingEnterData,
                    bookedSeat));
            boolean isNotExistingPassenger = notExistingPassenger.isNotExistingPassenger();
            passengerService
                    .updateSpecifiedPassenger(
                            parseUpdatedPassengerData(bookingEnterData, newReservation),
                            newPassenger,
                            isNotExistingPassenger
                    );
            passengerService.sendUpdatedPassengerEvent(newPassenger);

            return newReservation;
        } else {
            log.warn(RESERVATION_NOT_CREATED);

            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        Reservation savedReservation = reservationService.retrieveReservationById(reservationId);
        Passenger associatedPassengerDataEntity = passengerService.retrievePassengerByEmail(savedReservation
                .getPassengerEmail());
        String bookedSeat = savedReservation.getSeatNumber();
        flightService.makeSpecifiedBookedSeatFree(bookedSeat, savedReservation.getFlightNumber());
        List<Reservation> associatedPassengerReservationListEntity = associatedPassengerDataEntity.getReservationsList();
        associatedPassengerReservationListEntity.remove(savedReservation);
        reservationService.deleteReservationById(reservationId);
        passengerService.sendUpdatedPassengerEvent(associatedPassengerDataEntity);
        if (associatedPassengerReservationListEntity.isEmpty()) {
            passengerService.deletePassengerByEmail(savedReservation.getPassengerEmail());
        }
    }

    private Passenger parsePassengerData(BookingEnterData bookingEnterData) {
        return new Passenger(null,
                null,
                bookingEnterData.getPassengerEmail(),
                bookingEnterData.getName(),
                bookingEnterData.getSurname(),
                bookingEnterData.getPassengerBirthDate(),
                bookingEnterData.isDisability(),
                null);
    }

    private Passenger parseUpdatedPassengerData(BookingEnterData bookingEnterData,
                                                             Reservation newReservation) {
        return new Passenger(null,
                null,
                bookingEnterData.getPassengerEmail(),
                bookingEnterData.getName(),
                bookingEnterData.getSurname(),
                bookingEnterData.getPassengerBirthDate(),
                bookingEnterData.isDisability(),
                Collections.singletonList(newReservation));
    }

    private Passenger createPassenger(BookingEnterData bookingEnterData, ExistingPassenger existingPassenger) {
        String passengerEmail = bookingEnterData.getPassengerEmail();
        if (!passengerService.exists(passengerEmail)) {
            existingPassenger.setNotExistingPassenger(true);

            return passengerService.createNewPassenger(parsePassengerData(bookingEnterData)
            );
        } else {
            return passengerService.retrievePassengerByEmail(passengerEmail);
        }
    }

    private Reservation parseReservationData(BookingEnterData bookingEnterData,
                                                          String bookedSeat) {
        return new Reservation(
                null,
                bookingEnterData.getFlightName(),
                bookedSeat,
                bookingEnterData.getSeatClassType(),
                bookingEnterData.getPassengerEmail(),
                null);
    }
}

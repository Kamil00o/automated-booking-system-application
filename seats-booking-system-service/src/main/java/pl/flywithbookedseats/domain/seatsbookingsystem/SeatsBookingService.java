package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.api.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.external.storage.reservation.Reservation;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.domain.reservation.ReservationBusinessLogic;

import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@Slf4j
@RequiredArgsConstructor
public class SeatsBookingService {

    private final FlightService flightService;
    private final PassengerBusinessLogic passengerBL;
    private final ReservationBusinessLogic reservationBL;
    private final ReservationDtoMapper reservationDtoMapper;
    private final PassengerDtoMapper passengerDtoMapper;

    public ReservationDto bookSeatsInThePlane(BookingEnterData bookingEnterData) {
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
            newReservation = reservationBL.generateNewReservation(parseReservationData(bookingEnterData,
                    bookedSeat));
            boolean isNotExistingPassenger = notExistingPassenger.isNotExistingPassenger();
            passengerBL.updateSpecifiedPassenger(parseUpdatedPassengerData(bookingEnterData, newReservation),
                    newPassenger, isNotExistingPassenger);
            passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(newPassenger));
            return reservationDtoMapper.apply(newReservation);
        } else {
            log.warn(RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        Reservation savedReservation = reservationBL.retrieveReservationEntityFromDb(reservationId);
        Passenger associatedPassengerData = passengerBL.retrievePassengerEntityFromDb(savedReservation
                .getPassengerEmail());
        String bookedSeat = savedReservation.getSeatNumber();
        flightService.makeSpecifiedBookedSeatFree(bookedSeat, savedReservation.getFlightNumber());
        List<Reservation> associatedPassengerReservationList = associatedPassengerData.getReservationsList();
        associatedPassengerReservationList.remove(savedReservation);
        reservationBL.deleteReservationById(reservationId);
        passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(associatedPassengerData));
        if (associatedPassengerReservationList.isEmpty()) {
            passengerBL.deletePassengerByEmail(savedReservation.getPassengerEmail());
        }
    }

    private CreatePassengerCommand parsePassengerData(BookingEnterData bookingEnterData) {
        return new CreatePassengerCommand(null,
                bookingEnterData.getPassengerEmail(),
                bookingEnterData.getName(),
                bookingEnterData.getSurname(),
                bookingEnterData.getPassengerBirthDate(),
                bookingEnterData.isDisability(),
                null);
    }

    private UpdatePassengerCommand parseUpdatedPassengerData(BookingEnterData bookingEnterData,
                                                             Reservation newReservation) {
        return new UpdatePassengerCommand(null,
                bookingEnterData.getPassengerEmail(),
                bookingEnterData.getName(),
                bookingEnterData.getSurname(),
                bookingEnterData.getPassengerBirthDate(),
                bookingEnterData.isDisability(),
                Collections.singletonList(newReservation.getId()));
    }

    private Passenger createPassenger(BookingEnterData bookingEnterData, ExistingPassenger existingPassenger) {
        String passengerEmail = bookingEnterData.getPassengerEmail();
        if (!passengerBL.exists(passengerEmail)) {
            existingPassenger.setNotExistingPassenger(true);
            return passengerBL.generateNewPassenger(parsePassengerData(bookingEnterData)
            );
        } else {
            return passengerBL.retrievePassengerEntityFromDb(passengerEmail);
        }
    }

    private CreateReservationCommand parseReservationData(BookingEnterData bookingEnterData,
                                                          String bookedSeat) {
        return new CreateReservationCommand(bookingEnterData.getFlightName(),
                bookedSeat,
                bookingEnterData.getSeatClassType(),
                bookingEnterData.getPassengerEmail());
    }
}

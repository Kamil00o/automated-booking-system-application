package pl.flywithbookedseats.logic.service.implementation.seatsbookingsystem;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.kafka.RequestType;
import pl.flywithbookedseats.logic.exceptions.FlightNotFoundException;
import pl.flywithbookedseats.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.model.domain.Reservation;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.logic.service.implementation.flight.FlightBusinessLogic;
import pl.flywithbookedseats.logic.service.implementation.reservation.ReservationBusinessLogic;

import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.logic.service.implementation.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.logic.service.implementation.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@AllArgsConstructor
@Component
public class SeatsBookingBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingBusinessLogic.class);

    private final FlightBusinessLogic flightBL;
    private final PassengerBusinessLogic passengerBL;
    private final ReservationBusinessLogic reservationBL;
    private final ReservationDtoMapper reservationDtoMapper;
    private final PassengerDtoMapper passengerDtoMapper;

    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        Passenger newPassenger;
        Reservation newReservation;
        String bookedSeat;
        String flightName = bookingEnterDataCommand.flightName();
        ExistingPassenger notExistingPassenger = new ExistingPassenger(false);


        if (flightBL.exists(flightName)) {
            newPassenger = createPassenger(bookingEnterDataCommand, notExistingPassenger);
            bookedSeat = flightBL.bookSeatInFlightSeatsScheme(flightName,
                    bookingEnterDataCommand.seatClassType(),
                    newPassenger.getId(),
                    bookingEnterDataCommand.disability(),
                    bookingEnterDataCommand.passengerBirthDate());
            newReservation = reservationBL.generateNewReservation(parseReservationData(bookingEnterDataCommand,
                    bookedSeat));
            boolean isNotExistingPassenger = notExistingPassenger.isNotExistingPassenger();
            passengerBL.updateSpecifiedPassenger(parseUpdatedPassengerData(bookingEnterDataCommand, newReservation),
                    newPassenger, isNotExistingPassenger);
            passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(newPassenger));
            return reservationDtoMapper.apply(newReservation);
        } else {
            logger.warn(RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        Reservation savedReservation = reservationBL.retrieveReservationEntityFromDb(reservationId);
        Passenger associatedPassengerData = passengerBL.retrievePassengerEntityFromDb(savedReservation
                .getPassengerEmail());
        String bookedSeat = savedReservation.getSeatNumber();
        flightBL.makeSpecifiedBookedSeatFree(bookedSeat, savedReservation.getFlightNumber());
        List<Reservation> associatedPassengerReservationList = associatedPassengerData.getReservationsList();
        associatedPassengerReservationList.remove(savedReservation);
        reservationBL.deleteReservationById(reservationId);
        passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(associatedPassengerData));
        if (associatedPassengerReservationList.isEmpty()) {
            passengerBL.deletePassengerByEmail(savedReservation.getPassengerEmail());
        }
    }

    private CreatePassengerCommand parsePassengerData(BookingEnterDataCommand bookingEnterDataCommand) {
        return new CreatePassengerCommand(null,
                bookingEnterDataCommand.passengerEmail(),
                bookingEnterDataCommand.name(),
                bookingEnterDataCommand.surname(),
                bookingEnterDataCommand.passengerBirthDate(),
                bookingEnterDataCommand.disability(),
                null);
    }

    private UpdatePassengerCommand parseUpdatedPassengerData(BookingEnterDataCommand bookingEnterDataCommand,
                                                             Reservation newReservation) {
        return new UpdatePassengerCommand(null,
                bookingEnterDataCommand.passengerEmail(),
                bookingEnterDataCommand.name(),
                bookingEnterDataCommand.surname(),
                bookingEnterDataCommand.passengerBirthDate(),
                bookingEnterDataCommand.disability(),
                Collections.singletonList(newReservation.getId()));
    }

    private Passenger createPassenger(BookingEnterDataCommand bookingEnterDataCommand, ExistingPassenger existingPassenger) {
        String passengerEmail = bookingEnterDataCommand.passengerEmail();
        if (!passengerBL.exists(passengerEmail)) {
            existingPassenger.setNotExistingPassenger(true);
            return passengerBL.generateNewPassenger(parsePassengerData(bookingEnterDataCommand)
            );
        } else {
            return passengerBL.retrievePassengerEntityFromDb(passengerEmail);
        }
    }

    private CreateReservationCommand parseReservationData(BookingEnterDataCommand bookingEnterDataCommand,
                                                          String bookedSeat) {
        return new CreateReservationCommand(bookingEnterDataCommand.flightName(),
                bookedSeat,
                bookingEnterDataCommand.seatClassType(),
                bookingEnterDataCommand.passengerEmail());
    }
}

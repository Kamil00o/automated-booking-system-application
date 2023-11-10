package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsbookingsystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.FlightNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.reservation.ReservationDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.BookingEnterDataCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Passenger;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.Reservation;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.ReservationDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationBusinessLogic;

import java.util.Collections;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@AllArgsConstructor
@Component
public class SeatsBookingBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingBusinessLogic.class);

    private final FlightBusinessLogic flightBL;
    private final PassengerBusinessLogic passengerBL;
    private final ReservationBusinessLogic reservationBL;
    private final ReservationDtoMapper reservationDtoMapper;

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
            return reservationDtoMapper.apply(newReservation);
        } else {
            logger.warn(RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
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

@Getter
@Setter
class ExistingPassenger {

    public boolean notExistingPassenger = false;

    public ExistingPassenger(boolean notExistingPassenger) {
        this.notExistingPassenger = notExistingPassenger;
    }
}

package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.logic.mapper.passenger.PassengerDtoMapper;
import pl.flywithbookedseats.api.seatsbookingsystem.BookingEnterDataCommand;
import pl.flywithbookedseats.logic.model.command.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.logic.model.command.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.logic.service.implementation.passenger.PassengerBusinessLogic;
import pl.flywithbookedseats.api.reservation.ReservationDtoMapper1;
import pl.flywithbookedseats.domain.flight.FlightBusinessLogic;
import pl.flywithbookedseats.domain.reservation.ReservationBusinessLogic;

import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@AllArgsConstructor
@Component
public class SeatsBookingBusinessLogic {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingBusinessLogic.class);

    private final FlightBusinessLogic flightBL;
    private final PassengerBusinessLogic passengerBL;
    private final ReservationBusinessLogic reservationBL;
    private final ReservationDtoMapper1 reservationDtoMapper1;
    private final PassengerDtoMapper passengerDtoMapper;

    public ReservationDto bookSeatsInThePlane(BookingEnterDataCommand bookingEnterDataCommand) {
        Passenger newPassenger;
        ReservationEntity newReservationEntity;
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
            newReservationEntity = reservationBL.generateNewReservation(parseReservationData(bookingEnterDataCommand,
                    bookedSeat));
            boolean isNotExistingPassenger = notExistingPassenger.isNotExistingPassenger();
            passengerBL.updateSpecifiedPassenger(parseUpdatedPassengerData(bookingEnterDataCommand, newReservationEntity),
                    newPassenger, isNotExistingPassenger);
            passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(newPassenger));
            return reservationDtoMapper1.apply(newReservationEntity);
        } else {
            logger.warn(RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        ReservationEntity savedReservationEntity = reservationBL.retrieveReservationEntityFromDb(reservationId);
        Passenger associatedPassengerData = passengerBL.retrievePassengerEntityFromDb(savedReservationEntity
                .getPassengerEmail());
        String bookedSeat = savedReservationEntity.getSeatNumber();
        flightBL.makeSpecifiedBookedSeatFree(bookedSeat, savedReservationEntity.getFlightNumber());
        List<ReservationEntity> associatedPassengerReservationListEntity = associatedPassengerData.getReservationsList();
        associatedPassengerReservationListEntity.remove(savedReservationEntity);
        reservationBL.deleteReservationById(reservationId);
        passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper.apply(associatedPassengerData));
        if (associatedPassengerReservationListEntity.isEmpty()) {
            passengerBL.deletePassengerByEmail(savedReservationEntity.getPassengerEmail());
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
                                                             ReservationEntity newReservationEntity) {
        return new UpdatePassengerCommand(null,
                bookingEnterDataCommand.passengerEmail(),
                bookingEnterDataCommand.name(),
                bookingEnterDataCommand.surname(),
                bookingEnterDataCommand.passengerBirthDate(),
                bookingEnterDataCommand.disability(),
                Collections.singletonList(newReservationEntity.getId()));
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

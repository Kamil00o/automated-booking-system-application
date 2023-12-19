package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.flywithbookedseats.domain.flight.FlightNotFoundException;
import pl.flywithbookedseats.domain.flight.FlightService;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;
import pl.flywithbookedseats.external.storage.reservation.JpaReservationRepositoryMapper;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;
import pl.flywithbookedseats.api.passenger.PassengerDtoMapper1;
import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.api.passenger.UpdatePassengerCommand;
import pl.flywithbookedseats.domain.passenger.PassengerBusinessLogic;

import java.util.Collections;
import java.util.List;

import static pl.flywithbookedseats.domain.flight.FlightConstImpl.FLIGHT_NOT_FOUND_FLIGHT_NAME;
import static pl.flywithbookedseats.domain.reservation.ReservationConstsImpl.RESERVATION_NOT_CREATED;

@Slf4j
@RequiredArgsConstructor
public class SeatsBookingService {

    private final FlightService flightService;
    private final PassengerBusinessLogic passengerBL;
    private final ReservationService reservationService;
    private final PassengerDtoMapper1 passengerDtoMapper1;
    private final JpaReservationRepositoryMapper temporaryReservationMapper;

    public Reservation bookSeatsInThePlane(BookingEnterData bookingEnterData) {
        PassengerEntity newPassengerEntity;
        Reservation newReservation;
        String bookedSeat;
        String flightName = bookingEnterData.getFlightName();
        ExistingPassenger notExistingPassenger = new ExistingPassenger(false);


        if (flightService.exists(flightName)) {
            newPassengerEntity = createPassenger(bookingEnterData, notExistingPassenger);
            bookedSeat = flightService.bookSeatInFlightSeatsScheme(flightName,
                    bookingEnterData.getSeatClassType(),
                    newPassengerEntity.getId(),
                    bookingEnterData.isDisability(),
                    bookingEnterData.getPassengerBirthDate());
            newReservation = reservationService.generateNewReservation(parseReservationData(bookingEnterData,
                    bookedSeat));
            boolean isNotExistingPassenger = notExistingPassenger.isNotExistingPassenger();
            passengerBL
                    .updateSpecifiedPassenger(
                            parseUpdatedPassengerData(
                                    bookingEnterData,
                                    temporaryReservationMapper.toEntityy(newReservation)
                            ),
                            newPassengerEntity,
                            isNotExistingPassenger
                    );
            passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper1.apply(newPassengerEntity));
            return newReservation;
        } else {
            log.warn(RESERVATION_NOT_CREATED);
            throw new FlightNotFoundException(FLIGHT_NOT_FOUND_FLIGHT_NAME.formatted(flightName));
        }
    }

    public void deleteBookedReservationAndAssociatedData(Long reservationId) {
        Reservation savedReservation = reservationService.retrieveReservationById(reservationId);
        PassengerEntity associatedPassengerDataEntity = passengerBL.retrievePassengerEntityFromDb(savedReservation
                .getPassengerEmail());
        String bookedSeat = savedReservation.getSeatNumber();
        flightService.makeSpecifiedBookedSeatFree(bookedSeat, savedReservation.getFlightNumber());
        List<ReservationEntity> associatedPassengerReservationListEntity = associatedPassengerDataEntity.getReservationsList();
        //TODO: JpaReservationRepositoryMapper is used below until passengerEntity domain will be not refactored!!!!!
        associatedPassengerReservationListEntity.remove(temporaryReservationMapper.toEntityy(savedReservation));
        reservationService.deleteReservationById(reservationId);
        passengerBL.sendUpdatedPassengerEvent(passengerDtoMapper1.apply(associatedPassengerDataEntity));
        if (associatedPassengerReservationListEntity.isEmpty()) {
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
                                                             ReservationEntity newReservationEntity) {
        return new UpdatePassengerCommand(null,
                bookingEnterData.getPassengerEmail(),
                bookingEnterData.getName(),
                bookingEnterData.getSurname(),
                bookingEnterData.getPassengerBirthDate(),
                bookingEnterData.isDisability(),
                Collections.singletonList(newReservationEntity.getId()));
    }

    private PassengerEntity createPassenger(BookingEnterData bookingEnterData, ExistingPassenger existingPassenger) {
        String passengerEmail = bookingEnterData.getPassengerEmail();
        if (!passengerBL.exists(passengerEmail)) {
            existingPassenger.setNotExistingPassenger(true);
            return passengerBL.generateNewPassenger(parsePassengerData(bookingEnterData)
            );
        } else {
            return passengerBL.retrievePassengerEntityFromDb(passengerEmail);
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

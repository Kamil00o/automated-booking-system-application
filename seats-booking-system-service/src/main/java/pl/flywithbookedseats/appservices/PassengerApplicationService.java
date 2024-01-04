package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.passenger.PagePassenger;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PassengerApplicationService {

    private final PassengerService passengerService;
    private final ReservationService reservationService;

    @Transactional
    public Passenger createNewPassenger(Passenger passenger) {
        Passenger createdPassenger = passengerService.createNewPassenger(passenger);
        passengerService.sendAppropriatePassengerEvent(passenger);

        return createdPassenger;
    }

    @Transactional
    public Long createNewPassengerAndAssignHimReservations(Passenger passenger, List<Long> reservationIdList) {

        passenger.setReservationsList(null); //TODO

        Passenger createdPassenger = passengerService.createNewPassenger(passenger);
        for (Long id : reservationIdList) {
            Reservation reservation = reservationService.retrieveReservationById(id);
            reservation.setPassengerEmail(createdPassenger.getEmail());
            reservation.setPassenger(createdPassenger);
            reservationService.updateReservationById(reservation, reservation.getId());
        }
        passengerService.sendAppropriatePassengerEvent(createdPassenger);
        return createdPassenger.getId();
    }

    @Transactional
    public Passenger updatePassengerByEmail(Passenger passenger, String email) {
        Passenger updatedPassenger = passengerService.updatePassengerByEmail(passenger, email);
        passengerService.sendUpdatedPassengerEvent(updatedPassenger);

        return updatedPassenger;
    }

    @Transactional
    public Passenger updatePassengerById(Passenger passenger, Long id) {
        Passenger updatedPassenger = passengerService.updatePassengerById(passenger, id);
        passengerService.sendUpdatedPassengerEvent(updatedPassenger);

        return updatedPassenger;
    }

    public Passenger retrievePassengerByEmail(String email) {
        return passengerService.retrievePassengerByEmail(email);
    }

    public Passenger retrievePassengerById(Long id) {
        return passengerService.retrievePassengerById(id);
    }

    public Passenger retrievePassengerByPassengerServiceId(Long id) {
        return passengerService.retrievePassengerByPassengerServiceId(id);
    }

    public PagePassenger retrieveAllPassengers(Pageable pageable) {
        return passengerService.retrieveAllPassengers(pageable);
    }

    public void publishRequestedPassengerEvent(Passenger passenger) {
        passengerService.sendRequestedPassengerEvent(passenger);
    }

    @Transactional
    public void deleteAllPassengers() {
        passengerService.deleteAllPassengers();
    }

    @Transactional
    public void deletePassengerById(Long id) {
        passengerService.deletePassengerById(id);
    }

    @Transactional
    public void deletePassengerByEmail(String email) {
        passengerService.deletePassengerByEmail(email);
    }
}

package pl.flywithbookedseats.domain.reservation;

import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.api.reservation.ReservationDto;

import java.util.List;

public interface ReservationService {

    ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand);

    ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand, Long id);

    List<ReservationDto> retrieveAllReservations();

    ReservationDto retrieveReservationById(Long id);

    List<ReservationDto> retrieveReservationByEmail(String email);

    void deleteAllReservations();

    void deleteReservationById(Long id);

}

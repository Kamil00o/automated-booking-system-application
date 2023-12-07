package pl.flywithbookedseats.logic.service;

import pl.flywithbookedseats.logic.model.command.reservation.CreateReservationCommand;
import pl.flywithbookedseats.logic.model.command.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.logic.model.dto.ReservationDto;

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
package pl.flywithbookedseats.appservices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.domain.reservation.ReservationService;
import pl.flywithbookedseats.domain.reservation.ReservationService1;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationApplicationService {

    private final ReservationService service;

    public ReservationDto addNewReservationToDb(CreateReservationCommand createReservationCommand) {
        return null;
    }

    public ReservationDto updateReservationById(UpdateReservationCommand updateReservationCommand, Long id) {
        return null;
    }

    public List<ReservationDto> retrieveAllReservations() {
        return null;
    }

    public ReservationDto retrieveReservationById(Long id) {
        return null;
    }

    public List<ReservationDto> retrieveReservationByEmail(String email) {
        return null;
    }

    public void deleteAllReservations() {

    }

    public void deleteReservationById(Long id) {

    }
}

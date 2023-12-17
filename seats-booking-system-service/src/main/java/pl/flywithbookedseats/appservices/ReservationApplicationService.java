package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.reservation.CreateReservationCommand;
import pl.flywithbookedseats.api.reservation.ReservationDto;
import pl.flywithbookedseats.api.reservation.UpdateReservationCommand;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;
import pl.flywithbookedseats.domain.reservation.ReservationService1;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationApplicationService {

    private final ReservationService service;

    @Transactional
    public Reservation addNewReservationToDb(Reservation reservation) {
        return service.addNewReservationToDb(reservation);
    }

    @Transactional
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

    @Transactional
    public void deleteAllReservations() {

    }

    @Transactional
    public void deleteReservationById(Long id) {

    }
}

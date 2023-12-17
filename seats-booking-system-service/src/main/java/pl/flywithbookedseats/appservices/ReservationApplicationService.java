package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.reservation.PageReservation;
import pl.flywithbookedseats.domain.reservation.Reservation;
import pl.flywithbookedseats.domain.reservation.ReservationService;

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
    public Reservation updateReservationById(Reservation reservation, Long id) {
        return service.updateReservationById(reservation, id);
    }

    public PageReservation retrieveAllReservations(Pageable pageable) {
        return service.retrieveAllReservations(pageable);
    }

    public Reservation retrieveReservationById(Long id) {
        return service.retrieveReservationById(id);
    }

    public List<Reservation> retrieveReservationByEmail(String passengerEmail) {
        return service.retrieveReservationByEmail(passengerEmail);
    }

    @Transactional
    public void deleteAllReservations() {
        service.deleteAllReservations();
    }

    @Transactional
    public void deleteReservationById(Long id) {
        service.deleteReservationById(id);
    }
}

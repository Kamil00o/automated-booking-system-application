package pl.flywithbookedseats.domain.reservation;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsBySeatNumber(String seatNumber);

    Reservation findById(Long id);

    List<Reservation> findAllByPassengerEmail(String passengerEmail);

    Reservation findBySeatNumber(String seatNumber);

    List<Reservation> findByFlightNumber(String flightNumber);

    void deleteById(Long id);

    void deleteAll();

    PageReservation findAll(Pageable pageable);

}

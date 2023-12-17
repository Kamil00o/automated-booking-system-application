package pl.flywithbookedseats.domain.reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsBySeatNumber(String seatNumber);

    Reservation findById(Long id);

    List<Reservation> findAllByPassengerEmail(String passengerEmail);

}

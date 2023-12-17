package pl.flywithbookedseats.domain.reservation;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsBySeatNumber(String seatNumber);

}

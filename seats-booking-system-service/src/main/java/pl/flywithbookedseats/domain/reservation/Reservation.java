package pl.flywithbookedseats.domain.reservation;

import lombok.*;
import pl.flywithbookedseats.domain.passenger.Passenger;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Reservation {

    private Long id;
    private String flightNumber;
    private String seatNumber;
    private String seatTypeClass;
    private String passengerEmail;
    private Passenger passenger;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(flightNumber, that.flightNumber) && Objects.equals(seatNumber, that.seatNumber) && Objects.equals(seatTypeClass, that.seatTypeClass) && Objects.equals(passengerEmail, that.passengerEmail) && Objects.equals(passenger, that.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightNumber, seatNumber, seatTypeClass, passengerEmail, passenger);
    }
}

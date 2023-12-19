package pl.flywithbookedseats.domain.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passenger {

    private Long id;
    private Long passengerServiceId;
    private String email;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private boolean disability;
    private List<ReservationEntity> reservationsList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return disability == passenger.disability && Objects.equals(id, passenger.id) && Objects.equals(passengerServiceId, passenger.passengerServiceId) && Objects.equals(email, passenger.email) && Objects.equals(name, passenger.name) && Objects.equals(surname, passenger.surname) && Objects.equals(birthDate, passenger.birthDate) && Objects.equals(reservationsList, passenger.reservationsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengerServiceId, email, name, surname, birthDate, disability, reservationsList);
    }
}

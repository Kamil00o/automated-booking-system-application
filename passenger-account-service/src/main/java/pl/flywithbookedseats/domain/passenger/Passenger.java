package pl.flywithbookedseats.domain.passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Passenger {

    Long id;
    String name;
    String surname;
    String email;
    LocalDate birthDate;
    boolean disability;
    List<Long> reservationsIdList;
    String nationality;
    String gender;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Passenger passenger = (Passenger) o;

        if (Objects.equals(id, passenger.id)) {
            return false;
        }

        if (Objects.equals(name, passenger.name)) {
            return false;
        }

        if (Objects.equals(surname, passenger.surname)) {
            return false;
        }

        if (Objects.equals(email, passenger.email)) {
            return false;
        }

        if (Objects.equals(disability, passenger.disability)) {
            return false;
        }

        if (Objects.equals(reservationsIdList, passenger.reservationsIdList)) {
            return false;
        }

        if (Objects.equals(nationality, passenger.nationality)) {
            return false;
        }

        if (Objects.equals(gender, passenger.gender)) {
            return false;
        }

        return true;
    }
}

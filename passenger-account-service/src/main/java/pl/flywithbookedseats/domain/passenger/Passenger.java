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
    String password;
    UserRole role;
    LocalDate birthDate;
    boolean disability;
    List<Long> reservationsIdList;
    String nationality;
    String gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return disability == passenger.disability
                && Objects.equals(id, passenger.id)
                && Objects.equals(name, passenger.name)
                && Objects.equals(surname, passenger.surname)
                && Objects.equals(email, passenger.email)
                && Objects.equals(password, passenger.password)
                && Objects.equals(role, passenger.role)
                && Objects.equals(birthDate, passenger.birthDate)
                && Objects.equals(reservationsIdList, passenger.reservationsIdList)
                && Objects.equals(nationality, passenger.nationality)
                && Objects.equals(gender, passenger.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, role, birthDate,
                disability, reservationsIdList, nationality, gender);
    }
}

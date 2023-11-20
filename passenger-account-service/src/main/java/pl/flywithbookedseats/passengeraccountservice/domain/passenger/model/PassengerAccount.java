package pl.flywithbookedseats.passengeraccountservice.domain.passenger.model;

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
public class PassengerAccount {

    Long id;
    String name;
    String surname;
    String email;
    LocalDate birthDate;
    boolean disability;
    List<Long> reservationIdList;
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

        final PassengerAccount passengerAccount = (PassengerAccount) o;

        if (Objects.equals(id, passengerAccount.id)) {
            return false;
        }

        if (Objects.equals(name, passengerAccount.name)) {
            return false;
        }

        if (Objects.equals(surname, passengerAccount.surname)) {
            return false;
        }

        if (Objects.equals(email, passengerAccount.email)) {
            return false;
        }

        if (Objects.equals(disability, passengerAccount.disability)) {
            return false;
        }

        if (Objects.equals(reservationIdList, passengerAccount.reservationIdList)) {
            return false;
        }

        if (Objects.equals(nationality, passengerAccount.nationality)) {
            return false;
        }

        if (Objects.equals(gender, passengerAccount.gender)) {
            return false;
        }

        return true;
    }
}

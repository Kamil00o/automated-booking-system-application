package pl.flywithbookedseats.domain.seatsbookingsystem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingEnterData {

    private String name;
    private String surname;
    private boolean disability;
    private String passengerEmail;
    private LocalDate passengerBirthDate;
    private String flightName;
    private String seatClassType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookingEnterData that)) return false;
        return disability == that.disability && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(passengerEmail, that.passengerEmail) && Objects.equals(passengerBirthDate, that.passengerBirthDate) && Objects.equals(flightName, that.flightName) && Objects.equals(seatClassType, that.seatClassType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, disability, passengerEmail, passengerBirthDate, flightName, seatClassType);
    }
}

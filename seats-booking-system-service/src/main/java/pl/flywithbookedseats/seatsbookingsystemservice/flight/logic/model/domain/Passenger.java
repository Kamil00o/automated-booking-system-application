package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "passenger_TABLE")
@Entity
public class Passenger {

    private Long id;
    private String passengerAccountEmail;
    private String passenger_name;
    private String passenger_surname;
    private LocalDate birthDate;
    private boolean disability;
    private List<Reservation> reservationsList;
}

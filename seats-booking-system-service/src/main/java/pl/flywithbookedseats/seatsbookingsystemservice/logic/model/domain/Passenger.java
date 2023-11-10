package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts.NOT_NULL_MESSAGE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passenger_TABLE")
@Entity
public class Passenger {

    @Id
    @SequenceGenerator(
            name = "passenger_id_gen",
            sequenceName = "passenger_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "passenger_id_gen"
    )
    private Long id;
    private Long passengerServiceId;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 9, message = "The email field should have at least 9 signs")
    private String email;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String name;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String surname;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Past
    private LocalDate birthDate;
    @NotNull(message = NOT_NULL_MESSAGE)
    private boolean disability;
    @JsonIgnore
    @OneToMany
    @JoinTable(
            name = "passenger_attached_reservations_table",
            joinColumns = @JoinColumn(name = "passenger_id"),
            inverseJoinColumns = @JoinColumn(name = "reservation_id")
    )
    private List<Reservation> reservationsList;
}

package pl.flywithbookedseats.external.storage.passenger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.flywithbookedseats.common.Consts;
import pl.flywithbookedseats.external.storage.reservation.ReservationEntity;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passenger_TABLE")
@Entity
public class PassengerEntity {

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
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 9, message = "The email field should have at least 9 signs")
    private String email;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String name;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String surname;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Past
    private LocalDate birthDate;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    private boolean disability;
    @JsonIgnore
    @OneToMany(mappedBy = "passengerEntity", cascade = CascadeType.ALL)
    private List<ReservationEntity> reservationsList;
}

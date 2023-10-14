package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation_TABLE")
@Entity
public class Reservation {



    @Id
    @SequenceGenerator(
            name = "reservation_seq",
            sequenceName = "reservation_seq_gen",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "reservation_seq_gen"
    )
    private Long id;
    @Column(name = "flight_number")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 4, message = "The flightNumber field should have at least 4 signs")
    private String flightNumber;
    @Column(name = "seat_number")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The seatNumber field should consists of 3 signs", max = 3)
    private String seatNumber;
    @Column(name = "seat_type_class")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The seatNumber field should consists of 3 signs", max = 3)
    private String seatTypeClass;
    @Column(name = "passenger_email")
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 9, message = "The passengerEmail field should have at least 9 signs")
    private String passengerEmail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Passenger passenger;
}

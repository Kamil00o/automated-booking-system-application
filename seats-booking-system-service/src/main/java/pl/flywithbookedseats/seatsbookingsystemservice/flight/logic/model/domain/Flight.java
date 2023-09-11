package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;
import java.util.List;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
@Table(
        name = "flight_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "flightName",
                        columnNames = "flight_name"
                )
        }
)
@Entity
public class Flight {

    @Id
    @SequenceGenerator(
            name = "flight_id_sequence",
            sequenceName = "flight_id_gen",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "flight_id_gen"
    )
    private Long id;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The flightName field should have at least 3 signs")
    @Column(name = "flight_name")
    private String flightName;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The planeType field should have at least 3 signs")
    @Column(name = "plane_type")
    private String planeType;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 5, message = "The passengerNameSurname field should have at least 5 signs")
    @Column(name = "passenger_name_surname")
    private List<String> passengerNameSurname;
    @Column(name = "plane_seats_scheme_model")
    private HashMap<String, HashMap<String, Long>> seatsSchemePlaneModel;

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightName='" + flightName + '\'' +
                ", planeType='" + planeType + '\'' +
                ", passengerNameSurname=" + passengerNameSurname +
                ", seatsSchemePlaneModel=" + seatsSchemePlaneModel +
                '}';
    }
}
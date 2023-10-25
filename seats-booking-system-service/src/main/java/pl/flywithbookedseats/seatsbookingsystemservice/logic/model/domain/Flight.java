package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts;

import java.util.HashMap;
import java.util.List;


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
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The flightName field should have at least 3 signs")
    @Column(name = "flight_name")
    private String flightName;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The planeType field should have at least 3 signs")
    @Column(name = "plane_type")
    private String planeType;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 5, message = "The passengerNameSurname field should have at least 5 signs")
    @Column(name = "passenger_name_surname")
    private List<String> passengerNameSurname;
    @Column(name = "plane_seats_scheme_model")
    @ElementCollection
    private HashMap<String, String> seatsSchemePlaneModel; //It needs to be redesigned probably

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

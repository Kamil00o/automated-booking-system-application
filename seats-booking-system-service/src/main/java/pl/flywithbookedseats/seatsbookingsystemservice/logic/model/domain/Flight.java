package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Map;

import static pl.flywithbookedseats.seatsbookingsystemservice.logic.common.Consts.NOT_NULL_MESSAGE;


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
            name = "flight_id_gen",
            sequenceName = "flight_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "flight_id_gen"
    )
    private Long id;
    @Column(name = "flight_service_id")
    private Long flightSerivceId;
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
    @Column(name = "flight_passenger_name_surname")
    private List<String> flightPassengerNameSurnameList;
    @NotNull(message = NOT_NULL_MESSAGE)
    @ElementCollection
    @CollectionTable(
            name = "flight_mapping_table",
            joinColumns = {@JoinColumn(name = "flight_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "seat_name")
    @Column(name = "passenger_name_surname")
    private Map<String, String> bookedSeatsInPlaneList;

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightSerivceId=" + flightSerivceId +
                ", flightName='" + flightName + '\'' +
                ", planeType='" + planeType + '\'' +
                ", flightPassengerNameSurnameList=" + flightPassengerNameSurnameList +
                ", bookedSeatsInPlaneList=" + bookedSeatsInPlaneList +
                '}';
    }
}

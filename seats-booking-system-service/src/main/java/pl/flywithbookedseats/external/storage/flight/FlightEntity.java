package pl.flywithbookedseats.external.storage.flight;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.flywithbookedseats.common.Consts;

import java.util.Map;


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
public class FlightEntity {

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
    private Long flightServiceId;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 4, message = "The flightName field should have at least 4 signs")
    @Column(name = "flight_name")
    private String flightName;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 4, message = "The planeTypeName field should have at least 4 signs")
    @Column(name = "plane_type_name")
    private String planeTypeName;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @ElementCollection
    @CollectionTable(
            name = "flight_mapping_table",
            joinColumns = {@JoinColumn(name = "flight_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "seat_name")
    @Column(name = "passenger_id")
    private Map<String, Long> bookedSeatsInPlaneMap;

    @Override
    public String toString() {
        return "FlightEntity{" +
                "id=" + id +
                ", flightServiceId=" + flightServiceId +
                ", flightName='" + flightName + '\'' +
                ", planeType='" + planeTypeName + '\'' +
                ", bookedSeatsInPlaneList=" + bookedSeatsInPlaneMap +
                '}';
    }
}

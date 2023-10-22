package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.common.Consts.NOT_NULL_MESSAGE;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "seats_scheme_model_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "planeModelName",
                        columnNames = "plane_model_name")
        }
)
@Data
@Entity
public class SeatsSchemeModel {

    @Id
    @SequenceGenerator(
            name = "seats_scheme_id_seq_generator",
            sequenceName = "seats_scheme_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seats_scheme_id_seq_generator"
    )
    private Long id;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The planeModelName field should have at least 3 signs")
    @Column(name = "plane_model_name")
    private String planeModelName;
    @NotNull(message = NOT_NULL_MESSAGE)
    @ElementCollection
    @CollectionTable(
            name = "seats_scheme_mapping_table",
            joinColumns = {@JoinColumn(name = "seats_scheme_model_id", referencedColumnName = "id")}
    )
    @MapKeyColumn(name = "seat_name")
    @Column(name = "seat_class_name")
    private Map<String, String> seatsSchemeMap;

    @Override
    public String toString() {
        return "SeatsSchemeModel{" +
                "id=" + id +
                ", planeModelName='" + planeModelName + '\'' +
                ", seatsSchemeMap=" + seatsSchemeMap +
                '}';
    }
}

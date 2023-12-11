package pl.flywithbookedseats.external.storage.seatsscheme;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.flywithbookedseats.common.Consts;

import java.util.Map;

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
public class SeatsSchemeEntity {

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
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The planeModelName field should have at least 3 signs")
    @Column(name = "plane_model_name")
    private String planeModelName;
    @NotNull(message = Consts.NOT_NULL_MESSAGE)
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
        return "SeatsSchemeEntity{" +
                "id=" + id +
                ", planeModelName='" + planeModelName + '\'' +
                ", seatsSchemeMap=" + seatsSchemeMap +
                '}';
    }
}

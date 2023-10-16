package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;

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
                        name = "planeModel",
                        columnNames = "plane_model")
        }
)
@Data
@Entity
public class SeatsSchemeModel {

    @Id
    @SequenceGenerator(
            name = "seats_scheme_id_sequence",
            sequenceName = "seats_scheme_id_gen",
            allocationSize = 1
    )
    private Long id;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 3, message = "The planeModel field should have at least 3 signs")
    @Column(name = "plane_model")
    private String planeModel;
    @NotNull(message = NOT_NULL_MESSAGE)
    @Column(name = "seats_scheme")
    private HashMap<String, HashMap<String, Long>> seatsScheme = new HashMap<>();

    @Override
    public String toString() {
        return "SeatsSchemeModel{" +
                "id=" + id +
                ", planeModel='" + planeModel + '\'' +
                ", seatsScheme=" + seatsScheme +
                '}';
    }
}

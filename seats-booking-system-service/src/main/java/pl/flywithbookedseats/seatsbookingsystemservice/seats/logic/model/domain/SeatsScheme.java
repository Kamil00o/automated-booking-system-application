package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seats_scheme_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(name = "plane_name_constraint", columnNames = "plane_model")
        }
)
public class SeatsScheme {

    @Id
    @SequenceGenerator(
            name = "plane_model_id_sequence",
            sequenceName = "plane_model_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "plane_model_id_seq"
    )
    @Column(name = "ID")
    private Long id;
    @NotBlank
    @NotNull
    @Size(min = 2, message = "Plane model name has to contain 2 characters at least!")
    @Column(name = "plane_model")
    private String planeModel;
    @NotNull
    @ElementCollection
    private HashMap<String, Long> SeatsPlaneScheme;
}

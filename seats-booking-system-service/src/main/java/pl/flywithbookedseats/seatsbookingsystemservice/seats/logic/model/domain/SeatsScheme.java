package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.HashMap;

@Entity
@Builder
@Table(
        name = "seats_scheme_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(name = "plane_name_constraint", columnNames = "plane_model")
        }
)
public class SeatsScheme {

    @NotBlank
    @NotBlank
    @Size(min = 2, message = "Plane model name has to contain 2 characters at least!")
    @Column(name = "plane_model")
    private String planeModel;
    @NotNull
    private HashMap<String, Long> SeatsPlaneScheme;
}

package pl.flywithbookedseats.seatsbookingsystemservice.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Builder
@Table(
        name = "seats_scheme_TABLE"
)
public class SeatsScheme {

}

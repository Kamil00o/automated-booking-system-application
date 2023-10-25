package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.flight;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@SchemaMapping
public record CreateFlight(

        @NotNull
        @NotBlank
        @Size(min = 2, max = 4, message = "Flight name has to contain max 4 characters and 2 at least!")
        @Column(name = "flight_name")
        String flightName,
        @NotNull
        @NotBlank
        @Column(name = "plane_type")
        String planeType,
        @NotNull
        @NotBlank
        @Column(name = "from_to_airports")
        String fromToAirports,
        @NotNull
        @NotBlank
        @Column(name = "passenger_account_id")
        Long passengerAccountID
) {}

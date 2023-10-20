package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@SchemaMapping
public record CreateSeatsScheme(

        @NotBlank
        @NotNull
        @Size(min = 2, message = "Plane model name has to contain 2 characters at least!")
        String planeModel
) {}

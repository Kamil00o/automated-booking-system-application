package pl.flywithbookedseats.passengeraccountservice.model.command;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDate;

@SchemaMapping
public record CreatePassengerAccount(

        @Size(min = 2, message = "The name should have at least 2 signs")
        String name,
        @NotBlank(message = "surname field can't be empty")
        @Size(min = 2, message = "The name should have at least 2 signs")
        String surname,
        @NotBlank(message = "email field can't be empty")
        @Size(min = 7, message = "The name should have at least 7 signs")
        String email,
        @Past
        @Column(name = "birth_date")
        LocalDate birthDate,
        @Column(name = "health_state")
        String healthState,
        @Column(name = "life_stage")
        String lifeStage
) {
}

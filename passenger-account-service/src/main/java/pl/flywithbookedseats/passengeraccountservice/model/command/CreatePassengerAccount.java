package pl.flywithbookedseats.passengeraccountservice.model.command;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

import java.time.LocalDate;

@SchemaMapping
public record CreatePassengerAccount(

        @NotNull(message = "The name field can't be null")
        @Size(min = 2, message = "The name should have at least 2 signs")
        String name,
        @NotBlank(message = "surname field can't be empty")
        @NotNull
        @Size(min = 2, message = "The name should have at least 2 signs")
        String surname,
        @NotBlank(message = "email field can't be empty")
        @NotNull
        @Size(min = 7, message = "The name should have at least 7 signs")
        String email,
        @Past
        @NotNull
        @Column(name = "birth_date")
        LocalDate birthDate,
        @NotNull
        @NotBlank
        @Column(name = "health_state")
        String healthState,
        @NotNull
        @NotBlank
        @Column(name = "life_stage")
        String lifeStage
) {}

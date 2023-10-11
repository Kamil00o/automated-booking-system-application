package pl.flywithbookedseats.passengeraccountservice.model.command;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public record UpdatePassengerAccount(

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
        boolean disability,
        @NotNull
        @NotBlank
        @Column(name = "reservation_id")
        List<String> reservationId,
        @NotBlank
        @NotNull
        @Size(min = 2, message = "The nationality field should have at least 2 signs")
        String nationality
) {}

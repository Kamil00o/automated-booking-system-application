package pl.flywithbookedseats.passengeraccountservice.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "passenger_account")
@Table(
        name = "passenger_account_TABLE",
        uniqueConstraints = {
                @UniqueConstraint(name = "email", columnNames = "email")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class PassengerAccount {

    @Id
    @SequenceGenerator(
            name = "passenger_account_squence",
            sequenceName = "customized_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator =  "customized_sequence"
    )
    @Column(
            name = "ID",
            updatable = false
    )
    private Long id;
    @NotNull(message = "The name field can't be null")
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String name;
    @NotBlank(message = "surname field can't be empty")
    @NotNull
    @Size(min = 2, message = "The name should have at least 2 signs")
    private String surname;
    @NotBlank(message = "email field can't be empty")
    @NotNull
    @Size(min = 7, message = "The name should have at least 7 signs")
    @Column(
            name = "email",
            unique = true
    )
    private String email;
    @Past
    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private boolean disability;
    @NotNull
    @NotBlank
    @Column(name = "reservation_id")
    private List<String> reservationId;
    @NotBlank
    @NotNull
    @Size(min = 2, message = "The nationality field should have at least 2 signs")
    private String nationality;

    @Override
    public String toString() {
        return "PassengerAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                ", disability=" + disability +
                ", reservationId=" + reservationId +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}

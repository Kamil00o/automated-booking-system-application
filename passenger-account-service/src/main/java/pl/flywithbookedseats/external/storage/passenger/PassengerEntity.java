<<<<<<<< HEAD:passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/external/storage/passenger/PassengerEntity.java
package pl.flywithbookedseats.passengeraccountservice.external.storage.passenger;
========
package pl.flywithbookedseats.passengeraccountservice.logic.model.domain;
>>>>>>>> 4cae5f3 (Caused by: java.lang.ClassNotFoundException:):passenger-account-service/src/main/java/pl/flywithbookedseats/passengeraccountservice/logic/model/domain/PassengerAccount.java

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

import static pl.flywithbookedseats.passengeraccountservice.logic.common.Consts.*;

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
@Getter
@Setter
public class PassengerEntity {

    @Id
    @SequenceGenerator(
            name = "passenger_account_id_gen",
            sequenceName = "passenger_account_seq_gen",
            allocationSize = 1
    )
    @GeneratedValue(
            generator =  "passenger_account_id_gen"
    )
    @Column(
            name = "ID",
            updatable = false
    )
    private Long id;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 2, message = NAME_MIN_FIELD_SIZE_MSG)
    private String name;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 2, message = SURNAME_MIN_FIELD_SIZE_MSG)
    private String surname;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 7, message = EMAIL_MIN_FIELD_SIZE_MSG)
    @Column(
            name = "email",
            unique = true
    )
    private String email;
    @Past(message = PAST_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @NotNull(message = NOT_NULL_MESSAGE)
    private boolean disability;
    @Column(name = "reservation_id")
    private List<Long> reservationIdList;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 2, message = NATIONALITY_MIN_FIELD_SIZE_MSG)
    private String nationality;
    @NotBlank(message = NOT_BLANK_MESSAGE)
    @NotNull(message = NOT_NULL_MESSAGE)
    @Size(min = 4, message = GENDER_MIN_FIELD_SIZE_MSG)
    private String gender;
}

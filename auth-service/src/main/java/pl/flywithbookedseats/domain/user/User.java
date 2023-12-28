package pl.flywithbookedseats.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long passengerServiceId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private UserRole role;
    private LocalDate birthDate;
    private boolean disability;
    private List<Long> reservationsIdList;
    private String nationality;
    private String gender;

    public User withPassword(String newPassword) {
        return new User(
                passengerServiceId,
                name,
                surname,
                email,
                newPassword,
                role,
                birthDate,
                disability,
                reservationsIdList,
                nationality,
                gender
                );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return disability == user.disability
                && Objects.equals(passengerServiceId, user.passengerServiceId)
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && role == user.role
                && Objects.equals(birthDate, user.birthDate)
                && Objects.equals(reservationsIdList, user.reservationsIdList)
                && Objects.equals(nationality, user.nationality)
                && Objects.equals(gender, user.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengerServiceId, name, surname, email, password, role, birthDate, disability, reservationsIdList, nationality, gender);
    }
}

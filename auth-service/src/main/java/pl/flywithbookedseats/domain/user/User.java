package pl.flywithbookedseats.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

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
}

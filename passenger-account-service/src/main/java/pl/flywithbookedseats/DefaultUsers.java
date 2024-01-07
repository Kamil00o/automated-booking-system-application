package pl.flywithbookedseats;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.PassengerService;
import pl.flywithbookedseats.domain.passenger.UserRole;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Log
public class DefaultUsers implements CommandLineRunner {

    private final PassengerService service;

    private final Passenger admin = new Passenger(
            null,
            "admin",
            "admin",
            "admin@poczta.pl",
            "$2a$10$KZWfCGeASbf/e9xfkSUbrOWGQpTY2pFDdqNjcYASTl7vV8dRHF2MS",
            UserRole.ADMIN,
            LocalDate.of(2024, 1, 7),
            false,
            null,
            "polish",
            "Male"
    );

    private final Passenger passenger = new Passenger(
            null,
            "passenger",
            "passenger",
            "passenger@poczta.pl",
            "$2a$10$51uhHVA4/r7AlNoNYzlOEeXZzc73mutHJxLJcnbKVvgdM5KLwuI3S",
            UserRole.USER,
            LocalDate.of(2024, 1, 7),
            false,
            null,
            "polish",
            "Male"
    );


    private final Passenger disabledPassenger = new Passenger(
            null,
            "disabledPassenger",
            "disabledPassenger",
            "disabledPassenger@poczta.pl",
            "$2a$10$dOpqRdRaMcMT9UPtr3HAOe7ri9YrCqd.DLxGdOvGs.URsMr1jjc3C",
            UserRole.USER,
            LocalDate.of(2024, 1, 7),
            true,
            null,
            "polish",
            "Male"
    );


    private final Passenger childPassenger = new Passenger(
            null,
            "childPassenger",
            "childPassenger",
            "childPassenger@poczta.pl",
            "$2a$10$Srxht.GXl5YQz/jEi7tGtep5xmUBz5o5kufSOuWXXAWEjsAf/glTW",
            UserRole.USER,
            LocalDate.now().minusYears(5),
            false,
            null,
            "polish",
            "Male"
    );

    @Override
    public void run(String... args) throws Exception {
        try {
            addPassenger(admin);
            addPassenger(passenger);
            addPassenger(disabledPassenger);
            addPassenger(childPassenger);
        } catch (Exception ex) {
            log.warning(ex.getMessage());
        }
    }

    @Transactional
    private void addPassenger(Passenger passenger) {
        service.createNewPassenger(passenger);
    }
}

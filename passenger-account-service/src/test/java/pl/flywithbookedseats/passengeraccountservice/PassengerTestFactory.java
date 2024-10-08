package pl.flywithbookedseats.passengeraccountservice;

import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.domain.passenger.Passenger;
import pl.flywithbookedseats.domain.passenger.UserRole;

import java.time.LocalDate;

public class PassengerTestFactory {

    private static int passengerSequence = 0;
    private static Long idSequence = 0L;

    public static Passenger createPassenger() {
        return generatePassengerAccount();
    }

    public static CreatePassengerCommand createCreateCommand() {
        return generateCreatePassengerAccountCommand();
    }

    private static Passenger generatePassengerAccount() {
        return new Passenger(idSequence++,
                "testPassengerName",
                "testPassengerSurname",
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                "testPassword",
                UserRole.USER,
                LocalDate.now().minusDays(1),
                false,
                null,
                "european",
                "male");
    }

    private static CreatePassengerCommand generateCreatePassengerAccountCommand() {
        return new CreatePassengerCommand("testPassengerName",
                "testPassengerSurname",
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                "testPassword",
                "USER",
                LocalDate.now().minusDays(1),
                false,
                null,
                "european",
                "male");
    }
}

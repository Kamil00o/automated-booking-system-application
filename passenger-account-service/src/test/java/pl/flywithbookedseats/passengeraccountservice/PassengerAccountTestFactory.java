package pl.flywithbookedseats.passengeraccountservice;

import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerAccountCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

import java.time.LocalDate;

public class PassengerAccountTestFactory {

    private static int passengerSequence = 0;
    private static Long idSequence = 0L;

    public static PassengerAccount createPassenger() {
        return generatePassengerAccount();
    }

    public static CreatePassengerAccountCommand createCreateCommand() {
        return generateCreatePassengerAccountCommand();
    }

    private static PassengerAccount generatePassengerAccount() {
        return new PassengerAccount(idSequence++,
                "testPassengerName",
                "testPassengerSurname",
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                LocalDate.now().minusDays(1),
                false,
                null,
                "european",
                "male");
    }

    private static CreatePassengerAccountCommand generateCreatePassengerAccountCommand() {
        return new CreatePassengerAccountCommand("testPassengerName",
                "testPassengerSurname",
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                LocalDate.now().minusDays(1),
                false,
                null,
                "european",
                "male");
    }
}

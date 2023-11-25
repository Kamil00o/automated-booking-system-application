package pl.flywithbookedseats.passengeraccountservice;

import pl.flywithbookedseats.passengeraccountservice.api.passenger.command.CreatePassengerCommand;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

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
                LocalDate.now().minusDays(1),
                false,
                null,
                "european",
                "male");
    }
}

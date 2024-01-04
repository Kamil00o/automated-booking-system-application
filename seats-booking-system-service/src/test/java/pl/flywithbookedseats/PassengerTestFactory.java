package pl.flywithbookedseats;

import pl.flywithbookedseats.api.passenger.CreatePassengerCommand;
import pl.flywithbookedseats.domain.passenger.Passenger;

import java.time.LocalDate;

public class PassengerTestFactory {

    private static int passengerSequence = 0;
    private static Long idSequence = 0L;

    public static Passenger createPassenger() {
        return generatePassenger();
    }

    public static CreatePassengerCommand createCreateCommand() {
        return generateCreatePassengerAccountCommand();
    }

    private static Passenger generatePassenger() {
        return new Passenger(
                idSequence++,
                idSequence++ + 10,
                "testPassengerName",
                "testPassengerSurname",
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                LocalDate.now().minusDays(1),
                false,
                null
        );
    }

    private static CreatePassengerCommand generateCreatePassengerAccountCommand() {
        return new CreatePassengerCommand(
                idSequence++ + 10,
                "testPassenger%s@gmail.com".formatted(passengerSequence++),
                "testPassengerName",
                "testPassengerSurname",
                LocalDate.now().minusDays(1),
                false,
                null
        );
    }
}

package pl.flywithbookedseats.passengeraccountservice;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

import java.time.LocalDate;

public class PassengerAccountTestFactory {

    private static int passengerSequence = 0;
    private static Long idSequence = 0L;

    public static PassengerAccount createPassenger() {
        return generatePassengerAccount();
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
}

package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.model.command.CreatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

import java.util.function.Function;

@Component
public class CreatePassengerAccountMapper implements Function<CreatePassengerAccount, PassengerAccount> {
    @Override
    public PassengerAccount apply(CreatePassengerAccount createPassengerAccount) {
        return PassengerAccount.builder()
                .name(createPassengerAccount.getName())
                .surname(createPassengerAccount.getSurname())
                .email(createPassengerAccount.getEmail())
                .birthDate(createPassengerAccount.getBirthDate())
                .disability(createPassengerAccount.isDisability())
                .reservationId(createPassengerAccount.getReservationId())
                .nationality(createPassengerAccount.getNationality())
                .build();
    }
}

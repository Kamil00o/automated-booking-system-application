package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import pl.flywithbookedseats.passengeraccountservice.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;

import java.util.function.Function;

public class UpdatePassengerAccountMapper implements Function<UpdatePassengerAccount, PassengerAccount> {
    @Override
    public PassengerAccount apply(UpdatePassengerAccount updatePassengerAccount) {
        return PassengerAccount.builder()
                .name(updatePassengerAccount.getName())
                .surname(updatePassengerAccount.getSurname())
                .email(updatePassengerAccount.getEmail())
                .birthDate(updatePassengerAccount.getBirthDate())
                .disability(updatePassengerAccount.isDisability())
                .nationality(updatePassengerAccount.getNationality())
                .build();
    }
}

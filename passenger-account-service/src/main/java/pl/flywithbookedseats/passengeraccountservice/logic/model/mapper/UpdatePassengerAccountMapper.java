package pl.flywithbookedseats.passengeraccountservice.logic.model.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.logic.model.command.UpdatePassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.logic.model.domain.PassengerAccount;

import java.util.function.Function;

@Component
public class UpdatePassengerAccountMapper implements Function<UpdatePassengerAccount, PassengerAccount> {
    @Override
    public PassengerAccount apply(UpdatePassengerAccount updatePassengerAccount) {
        return PassengerAccount.builder()
                .name(updatePassengerAccount.name())
                .surname(updatePassengerAccount.surname())
                .email(updatePassengerAccount.email())
                .birthDate(updatePassengerAccount.birthDate())
                .disability(updatePassengerAccount.disability())
                .nationality(updatePassengerAccount.nationality())
                .gender(updatePassengerAccount.gender())
                .build();
    }
}

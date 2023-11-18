package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.function.Function;

@Component
public class PassengerAccountDtoMapper implements Function<PassengerAccount, PassengerAccountDto> {

    @Override
    public PassengerAccountDto apply(PassengerAccount passengerAccount) {
        return PassengerAccountDto.builder()
                .passengerServiceId(passengerAccount.getId())
                .name(passengerAccount.getName())
                .surname(passengerAccount.getSurname())
                .email(passengerAccount.getEmail())
                .birthDate(passengerAccount.getBirthDate())
                .disability(passengerAccount.isDisability())
                .reservationsIdList(passengerAccount.getReservationIdList())
                .build();
    }
}

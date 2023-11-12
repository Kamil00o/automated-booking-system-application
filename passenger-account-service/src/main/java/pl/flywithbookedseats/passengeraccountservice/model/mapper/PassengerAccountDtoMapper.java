package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;

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
                .reservationId(passengerAccount.getReservationIdList())
                .build();
    }
}

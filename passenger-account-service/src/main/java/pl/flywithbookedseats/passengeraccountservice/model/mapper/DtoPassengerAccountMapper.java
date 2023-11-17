package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;

import java.util.function.Function;

@Component
public class DtoPassengerAccountMapper implements Function<PassengerAccountDto, PassengerAccount> {
    @Override
    public PassengerAccount apply(PassengerAccountDto passengerAccountDto) {
        return PassengerAccount.builder()
                .name(passengerAccountDto.name())
                .surname(passengerAccountDto.surname())
                .email(passengerAccountDto.email())
                .birthDate(passengerAccountDto.birthDate())
                .disability(passengerAccountDto.disability())
                .reservationIdList(passengerAccountDto.reservationIdList())
                .build();
    }
}

package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

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
                .reservationIdList(passengerAccountDto.reservationsIdList())
                .build();
    }
}

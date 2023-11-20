package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.function.Function;

@Component
public class DtoPassengerAccountMapper implements Function<PassengerAccountDto, PassengerAccountEntity> {
    @Override
    public PassengerAccountEntity apply(PassengerAccountDto passengerAccountDto) {
        return PassengerAccountEntity.builder()
                .name(passengerAccountDto.name())
                .surname(passengerAccountDto.surname())
                .email(passengerAccountDto.email())
                .birthDate(passengerAccountDto.birthDate())
                .disability(passengerAccountDto.disability())
                .reservationIdList(passengerAccountDto.reservationsIdList())
                .build();
    }
}

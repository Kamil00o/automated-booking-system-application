package pl.flywithbookedseats.passengeraccountservice.api.passenger.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.passengeraccountservice.external.storage.passenger.entity.PassengerAccountEntity;
import pl.flywithbookedseats.passengeraccountservice.api.passenger.dto.PassengerAccountDto;

import java.util.function.Function;

@Component
public class PassengerAccountDtoMapper implements Function<PassengerAccountEntity, PassengerAccountDto> {

    @Override
    public PassengerAccountDto apply(PassengerAccountEntity passengerAccountEntity) {
        return PassengerAccountDto.builder()
                .passengerServiceId(passengerAccountEntity.getId())
                .name(passengerAccountEntity.getName())
                .surname(passengerAccountEntity.getSurname())
                .email(passengerAccountEntity.getEmail())
                .birthDate(passengerAccountEntity.getBirthDate())
                .disability(passengerAccountEntity.isDisability())
                .reservationsIdList(passengerAccountEntity.getReservationIdList())
                .build();
    }
}

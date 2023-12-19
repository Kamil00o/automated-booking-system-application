package pl.flywithbookedseats.api.passenger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;

import java.util.function.Function;

@AllArgsConstructor
@Component
public class DtoPassengerMapper implements Function<PassengerDto, PassengerEntity> {

    private final PassengerConverter passengerConverter;

    @Override
    public PassengerEntity apply(PassengerDto passengerDto) {
        return PassengerEntity.builder()
                .passengerServiceId(passengerDto.passengerServiceId())
                .email(passengerDto.email())
                .name(passengerDto.name())
                .surname(passengerDto.surname())
                .birthDate(passengerDto.birthDate())
                .disability(passengerDto.disability())
                .reservationsList(passengerConverter.convertReservationIdListToEntityVersion(passengerDto.
                        reservationsIdList()))
                .build();
    }
}

package pl.flywithbookedseats.logic.mapper.passenger;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

import java.util.function.Function;

@AllArgsConstructor
@Component
public class DtoPassengerMapper implements Function<PassengerDto, Passenger> {

    private final PassengerConverter passengerConverter;

    @Override
    public Passenger apply(PassengerDto passengerDto) {
        return Passenger.builder()
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

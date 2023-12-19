package pl.flywithbookedseats.api.passeger;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.passenger.PassengerEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PassengerDtoMapper1 implements Function<PassengerEntity, PassengerDto> {
    @Override
    public PassengerDto apply(PassengerEntity passengerEntity) {
        return PassengerDto.builder()
                .passengerServiceId(passengerEntity.getPassengerServiceId())
                .email(passengerEntity.getEmail())
                .name(passengerEntity.getName())
                .surname(passengerEntity.getSurname())
                .birthDate(passengerEntity.getBirthDate())
                .disability(passengerEntity.isDisability())
                .reservationsIdList(generateReservationIdList(passengerEntity))
                .build();
    }

    private List<Long> generateReservationIdList(PassengerEntity passengerEntity) {
        List<Long> localReservationIdList = new ArrayList<Long>();
        if (passengerEntity.getReservationsList() != null) {
            passengerEntity.getReservationsList().forEach(reservation -> {
                localReservationIdList.add(reservation.getId());
            });
        }

        return localReservationIdList;
    }
}

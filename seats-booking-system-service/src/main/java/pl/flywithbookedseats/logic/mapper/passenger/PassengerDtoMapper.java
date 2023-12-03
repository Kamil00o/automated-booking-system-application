package pl.flywithbookedseats.logic.mapper.passenger;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.logic.model.domain.Passenger;
import pl.flywithbookedseats.logic.model.dto.PassengerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class PassengerDtoMapper implements Function<Passenger, PassengerDto> {
    @Override
    public PassengerDto apply(Passenger passenger) {
        return PassengerDto.builder()
                .passengerServiceId(passenger.getPassengerServiceId())
                .email(passenger.getEmail())
                .name(passenger.getName())
                .surname(passenger.getSurname())
                .birthDate(passenger.getBirthDate())
                .disability(passenger.isDisability())
                .reservationsIdList(generateReservationIdList(passenger))
                .build();
    }

    private List<Long> generateReservationIdList(Passenger passenger) {
        List<Long> localReservationIdList = new ArrayList<Long>();
        if (passenger.getReservationsList() != null) {
            passenger.getReservationsList().forEach(reservation -> {
                localReservationIdList.add(reservation.getId());
            });
        }

        return localReservationIdList;
    }
}

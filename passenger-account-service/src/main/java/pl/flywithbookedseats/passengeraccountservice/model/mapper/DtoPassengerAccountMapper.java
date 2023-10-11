package pl.flywithbookedseats.passengeraccountservice.model.mapper;

import pl.flywithbookedseats.passengeraccountservice.model.domain.PassengerAccount;
import pl.flywithbookedseats.passengeraccountservice.model.dto.PassengerAccountDto;

import java.util.function.Function;

public class DtoPassengerAccountMapper implements Function<PassengerAccountDto, PassengerAccount> {
    @Override
    public PassengerAccount apply(PassengerAccountDto passengerAccountDto) {
        return PassengerAccount.builder()
                .name(passengerAccountDto.getName())
                .surname(passengerAccountDto.getSurname())
                .email(passengerAccountDto.getEmail())
                .birthDate(passengerAccountDto.birthDate())
                .disability(passengerAccountDto.isDisability())
                .reservationId(passengerAccountDto.getReservationId())
                .build();
    }
}

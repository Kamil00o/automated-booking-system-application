package pl.flywithbookedseats.passengeraccountservice.api.passenger;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.Passenger;

import java.util.List;

public record PagePassengerAccountDto(
        List<Passenger> passengers,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}

package pl.flywithbookedseats.passengeraccountservice.api.passenger.dto;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.Passenger;

import java.util.List;

public record PagePassengerAccountDto(
        List<Passenger> passengers,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}

package pl.flywithbookedseats.passengeraccountservice.api.passenger.dto;

import pl.flywithbookedseats.passengeraccountservice.domain.passenger.model.PassengerAccount;

import java.util.List;

public record PagePassengerAccountDto(
        List<PassengerAccount> passengerAccounts,
        Long currentPage,
        Long totalPages,
        Long totalElements
) {}

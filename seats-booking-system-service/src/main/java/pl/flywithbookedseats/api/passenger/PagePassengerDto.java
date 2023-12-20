package pl.flywithbookedseats.api.passenger;

import pl.flywithbookedseats.domain.passenger.Passenger;

import java.util.List;

public record PagePassengerDto(

        List<Passenger> passengers,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}

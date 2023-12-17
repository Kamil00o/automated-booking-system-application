package pl.flywithbookedseats.api.flight;

import pl.flywithbookedseats.domain.flight.Flight;

import java.util.List;

public record PageFlightDto(

        List<Flight> flights,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}

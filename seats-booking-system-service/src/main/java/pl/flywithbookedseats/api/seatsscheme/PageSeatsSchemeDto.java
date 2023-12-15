package pl.flywithbookedseats.api.seatsscheme;

import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

import java.util.List;

public record PageSeatsSchemeDto(
        List<SeatsScheme> seatsSchemes,
        Integer currentPage,
        Integer totalPages,
        Long totalElements
) {}

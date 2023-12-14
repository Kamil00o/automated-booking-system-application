package pl.flywithbookedseats.api.seatsscheme;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record SeatsSchemeDto(
        Long id,
        String planeModelName,
        List<String> seatClassTypeList,
        Map<String, String> seatsSchemeMap
) {}

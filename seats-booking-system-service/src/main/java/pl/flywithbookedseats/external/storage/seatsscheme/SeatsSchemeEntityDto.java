package pl.flywithbookedseats.external.storage.seatsscheme;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record SeatsSchemeEntityDto(
        Long id,
        String planeModelName,
        List<String> seatClassTypeList,
        Map<String, String> seatsSchemeMap
) {}

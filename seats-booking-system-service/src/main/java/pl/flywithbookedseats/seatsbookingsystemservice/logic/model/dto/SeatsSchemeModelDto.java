package pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record SeatsSchemeModelDto(
        Long id,
        String planeModelName,
        List<String> seatClassTypeList,
        Map<String, String> seatsSchemeMap
) {}

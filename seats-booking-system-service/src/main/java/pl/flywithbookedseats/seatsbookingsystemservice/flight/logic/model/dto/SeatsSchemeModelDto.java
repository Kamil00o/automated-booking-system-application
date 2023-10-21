package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Builder
public record SeatsSchemeModelDto(
        Long id,
        String planeModelName,
        List<String> seatClassTypeList,
        HashMap<String, String> seatsSchemeMap
) {}

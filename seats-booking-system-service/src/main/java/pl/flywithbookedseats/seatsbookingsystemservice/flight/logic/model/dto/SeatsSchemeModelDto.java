package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Builder
public record SeatsSchemeModelDto(
        Long id,
        String planeModel,
        List<String> seatClassType,
        HashMap<String, String> seatsAndRowsInTheSeatsClassType
) {}

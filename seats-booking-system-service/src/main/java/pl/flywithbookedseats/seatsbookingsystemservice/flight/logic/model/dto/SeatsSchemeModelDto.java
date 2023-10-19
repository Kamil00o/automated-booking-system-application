package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;

@Builder
@Getter
@Setter
public record SeatsSchemeModelDto(
        Long id,
        String planeModel,
        List<String> seatClassType,
        HashMap<String, String> seatsAndRowsInTheSeatsClassType
) {}

package pl.flywithbookedseats.logic.model.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record FlightDto(
        Long flightServiceId,
        String flightName,
        String planeTypeName,
        Map<String, String> bookedSeatsInPlaneList
) {}

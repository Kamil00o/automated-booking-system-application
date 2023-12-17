package pl.flywithbookedseats.api.reservation;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.reservation.PageReservation;

@Mapper(componentModel = "spring")
public interface PageReservationDtoMapper {

    PageReservationDto toDto(PageReservation domain);
}

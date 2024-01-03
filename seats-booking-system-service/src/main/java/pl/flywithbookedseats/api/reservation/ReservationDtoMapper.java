package pl.flywithbookedseats.api.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.reservation.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    ReservationDto toDto(Reservation domain);

}

package pl.flywithbookedseats.api.reservation;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.reservation.Reservation;

@Mapper(componentModel = "spring")
public interface UpdateReservationCommandMapper {

    Reservation toDomain(UpdateReservationCommand command);

}

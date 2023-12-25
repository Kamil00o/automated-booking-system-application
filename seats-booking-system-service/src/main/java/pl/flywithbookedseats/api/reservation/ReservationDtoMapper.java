package pl.flywithbookedseats.api.reservation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.reservation.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationDtoMapper {

    @Mapping(source = "domain", target = "passengerEmail", qualifiedByName = "getPassengerEmail")
    ReservationDto toDto(Reservation domain);

    @Named("getPassengerEmail")
    default String retrievePassengerEmail(Reservation domain) {
        return domain.getPassenger().getEmail();
    }
}

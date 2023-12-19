package pl.flywithbookedseats.api.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerDtoMapper {

    @Mapping(source = "domain", target = "reservationsIdList", qualifiedByName = "convertedList")
    PassengerDto toDto(Passenger domain);

    @Named("convertedList")
    default List<Long> convertIntoIdList(Passenger domain) {
        List<Long> localReservationIdList = new ArrayList<Long>();
        if (domain.getReservationsList() != null) {
            domain.getReservationsList()
                    .forEach(reservation -> localReservationIdList.add(reservation.getId()));
        }

        return localReservationIdList;
    }
}

package pl.flywithbookedseats.external.message.passenger;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.passenger.Passenger;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface KafkaPassengerDtoMapper {


    Passenger toDomain(KafkaPassengerDto dto);

    @Mapping(source = "domain", target = "reservationsIdList", qualifiedByName = "convertedList")
    KafkaPassengerDto toDto(Passenger domain);

    @Named("convertedList")
    default List<Long> convertList(Passenger domain) {
        List<Long> convertedReservationIdList = new ArrayList<>();
        domain.getReservationsList().forEach(reservation -> convertedReservationIdList.add(reservation.getId()));

        return convertedReservationIdList;
    }

}

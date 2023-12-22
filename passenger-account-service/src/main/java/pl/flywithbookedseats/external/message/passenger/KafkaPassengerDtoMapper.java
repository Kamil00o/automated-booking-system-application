package pl.flywithbookedseats.external.message.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface KafkaPassengerDtoMapper {

    KafkaPassengerDto toDto(Passenger domain);

    Passenger toDomain(KafkaPassengerDto dto);

}

package pl.flywithbookedseats.external.message.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.passenger.Passenger;

@Mapper(componentModel = "spring")
public interface KafkaPassengerDtoMapper {

    Passenger toDomain(KafkaPassengerDto dto);

    KafkaPassengerDto toDto(Passenger domain);

}

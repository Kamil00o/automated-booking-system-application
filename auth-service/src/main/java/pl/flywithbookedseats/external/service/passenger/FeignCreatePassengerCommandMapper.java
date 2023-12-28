package pl.flywithbookedseats.external.service.passenger;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.user.User;

@Mapper(componentModel = "spring")
public interface FeignCreatePassengerCommandMapper {

    FeignCreatePassengerCommand toCommand(User domain);
}

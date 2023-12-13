package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeData;

@Mapper(componentModel = "spring")
public interface CreateSeatsSchemeCommandMapper {

    SeatsSchemeData toDomain(CreateSeatsSchemeCommand command);

}

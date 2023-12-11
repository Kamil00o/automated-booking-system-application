package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

@Mapper(componentModel = "spring")
public interface CreateSeatsSchemeCommandMapper {

    SeatsScheme toDomain(CreateSeatsSchemeCommand command);

}

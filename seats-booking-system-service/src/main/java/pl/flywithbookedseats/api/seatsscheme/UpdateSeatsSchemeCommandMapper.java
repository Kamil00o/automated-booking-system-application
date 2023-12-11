package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

@Mapper(componentModel = "spring")
public interface UpdateSeatsSchemeCommandMapper {

    SeatsScheme toDomain(UpdateSeatsSchemeCommand command);

}

package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface CreateSeatsSchemeCommandMapper {

    @Mapping(source = "command", target = "seatsSchemeMap", qualifiedByName = "mapParser")
    SeatsScheme toDomain(CreateSeatsSchemeCommand command);

    @Named("mapParser")
    default Map<String, String> seatsSchemeMapParser(CreateSeatsSchemeCommand command) {
        return SeatsSchemeFactory.seatsSchemeMapParser(command);
    }
}

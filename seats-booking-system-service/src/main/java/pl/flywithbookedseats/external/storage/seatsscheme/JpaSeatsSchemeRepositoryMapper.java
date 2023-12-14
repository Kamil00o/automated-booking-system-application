package pl.flywithbookedseats.external.storage.seatsscheme;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

@Mapper(componentModel = "spring")
public interface JpaSeatsSchemeRepositoryMapper {

    SeatsSchemeEntity toEntity(SeatsScheme domain);

    SeatsScheme toDomain(SeatsSchemeEntity entity);
}

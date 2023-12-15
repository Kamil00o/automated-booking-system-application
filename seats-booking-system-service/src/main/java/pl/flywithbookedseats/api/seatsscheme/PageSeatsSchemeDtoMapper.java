package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import pl.flywithbookedseats.domain.seatsscheme.PageSeatsScheme;

@Mapper(componentModel = "spring")
public interface PageSeatsSchemeDtoMapper {

    PageSeatsSchemeDto toDto(PageSeatsScheme domain);

}

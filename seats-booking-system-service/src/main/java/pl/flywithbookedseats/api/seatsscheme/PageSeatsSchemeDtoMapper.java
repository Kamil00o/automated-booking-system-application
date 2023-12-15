package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PageSeatsSchemeDtoMapper {

    PageSeatsSchemeDto toDto(PageSeatsSchemeDto domain);
}

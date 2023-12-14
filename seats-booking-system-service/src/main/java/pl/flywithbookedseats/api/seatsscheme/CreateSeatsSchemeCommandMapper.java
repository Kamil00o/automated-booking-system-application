package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeData;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CreateSeatsSchemeCommandMapper {

    @Mapping(source = "command", target = "seatClassTypeList", qualifiedByName = "myList")
    SeatsSchemeData toDomain(CreateSeatsSchemeCommand command);

    @Named("myList")
    default List<String> classTypeList(CreateSeatsSchemeCommand command) {
        List<String> test = new ArrayList<>();
        command.seatClassTypeList().forEach(x -> test.add(x + "xD"));
        return test;
    }
}

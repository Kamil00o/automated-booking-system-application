package pl.flywithbookedseats.api.seatsscheme;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Mapper(componentModel = "spring")
public interface SeatsSchemeDtoMapper {

    @Mapping(source = "domain", target = "seatClassTypeList", qualifiedByName = "listGenerator")
    @Mapping(source = "domain", target = "seatsSchemeMap", qualifiedByName = "sortMap")
    SeatsSchemeDto toDto(SeatsScheme domain);

    @Named("listGenerator")
    default List<String> generateseatClassTypeList(SeatsScheme domain) {
        Map<String, String> seatsSchemeMap = domain.getSeatsSchemeMap();
        List<String> localSeatClassTypeList = new ArrayList<>();
        for (Map.Entry<String, String> entry : seatsSchemeMap.entrySet()) {
            if (!localSeatClassTypeList.contains(entry.getValue())) {
                localSeatClassTypeList.add(entry.getValue());
            }
        }

        return localSeatClassTypeList;
    }

    @Named("sortMap")
    default Map<String, String> sortSeatsSchemeMap(SeatsScheme domain) {
        return new TreeMap<String, String>(domain.getSeatsSchemeMap());
    }
}

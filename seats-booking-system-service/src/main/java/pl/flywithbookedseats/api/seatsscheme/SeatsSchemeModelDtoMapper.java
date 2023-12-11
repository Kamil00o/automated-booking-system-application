package pl.flywithbookedseats.api.seatsscheme;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntity;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@Component
public class SeatsSchemeModelDtoMapper implements Function<SeatsSchemeEntity, SeatsSchemeEntityDto> {
    @Override
    public SeatsSchemeEntityDto apply(SeatsSchemeEntity seatsSchemeEntity) {
        return SeatsSchemeEntityDto.builder()
                .id(seatsSchemeEntity.getId())
                .planeModelName(seatsSchemeEntity.getPlaneModelName())
                .seatClassTypeList(generateseatClassTypeList(seatsSchemeEntity.getSeatsSchemeMap()))
                .seatsSchemeMap(new TreeMap<String, String>(seatsSchemeEntity.getSeatsSchemeMap()))
                .build();
    }

    private List<String> generateseatClassTypeList(Map<String, String> seatsSchemeMap) {
        List<String> localSeatClassTypeList = new ArrayList<>();
        for (Map.Entry<String, String> entry : seatsSchemeMap.entrySet()) {
            if (!localSeatClassTypeList.contains(entry.getValue())) {
                localSeatClassTypeList.add(entry.getValue());
            }
        }

        return localSeatClassTypeList;
    }
}

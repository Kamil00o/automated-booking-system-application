package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.seatsschememodel;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.SeatsSchemeModelDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@Component
public class SeatsSchemeModelDtoMapper implements Function<SeatsSchemeModel, SeatsSchemeModelDto> {
    @Override
    public SeatsSchemeModelDto apply(SeatsSchemeModel seatsSchemeModel) {
        return SeatsSchemeModelDto.builder()
                .id(seatsSchemeModel.getId())
                .planeModelName(seatsSchemeModel.getPlaneModelName())
                .seatClassTypeList(generateseatClassTypeList(seatsSchemeModel.getSeatsSchemeMap()))
                .seatsSchemeMap(new TreeMap<String, String>(seatsSchemeModel.getSeatsSchemeMap()))
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

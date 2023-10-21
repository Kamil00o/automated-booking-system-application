package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.SeatsSchemeModel;

import java.util.*;
import java.util.function.Function;

@Component
public class CreateSeatsSchemeModelMapper implements Function<CreateSeatsSchemeModelCommand, SeatsSchemeModel> {

    int prevclassSeatNumber;
    private static final String SEATS_FOLLOWING_LETTERS = "ABCDEFGHIJK";
    @Override
    public SeatsSchemeModel apply(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        return SeatsSchemeModel.builder()
                .planeModelName(createSeatsSchemeModelCommand.planeModelName())
                .seatsSchemeMap(seatsSchemeMapParser(createSeatsSchemeModelCommand))
                .build();
    }

    private Map<String, String> seatsSchemeMapParser(
            CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        Map<String, String> localSeatsSchemeMap = new HashMap<String, String>();
        List<String> seatsNamesList;
        int iterationCounter = 0;
        prevclassSeatNumber = 1;

        for (String classType : createSeatsSchemeModelCommand.seatClassTypeList()) {
            seatsNamesList = generateSeatsNames(createSeatsSchemeModelCommand, iterationCounter);
            generateSeatsSchemeModelMap(classType, localSeatsSchemeMap, seatsNamesList);
            iterationCounter++;
        }
        return localSeatsSchemeMap;
    }

    private Map<String, String> generateSeatsSchemeModelMap(String classTypeName,
            Map<String, String> localSeatsSchemeMap,
            List<String> localSeatsNamesList) {

        for (String seatName : localSeatsNamesList) {
            localSeatsSchemeMap.put(seatName, classTypeName);
        }
        return localSeatsSchemeMap;
    }

    private List<String> generateSeatsNames(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand,
                                            int iterationCounter) {

        List<String> localSeatsNamesList = new LinkedList<>();
        List<Integer> amountOfSeatsInARowList = createSeatsSchemeModelCommand.amountOfSeatsInARowPerSeatClassTypeList();
        List<Integer> amountOfRowsList = createSeatsSchemeModelCommand.amountOfRowsPerSeatClassTypeList();

        StringBuilder seatNumber = new StringBuilder();
        int seatsRowCounter = prevclassSeatNumber;
        for (int i = 0; i < amountOfSeatsInARowList.get(iterationCounter); i++) {
            for (int j = prevclassSeatNumber ; j < (amountOfRowsList.get(iterationCounter) + 1); j++) {
                seatNumber.append(j)
                        .append(SEATS_FOLLOWING_LETTERS.substring(i, i + 1));
                localSeatsNamesList.add(seatNumber.toString());
                seatNumber.delete(0, seatNumber.length());
                seatsRowCounter++;
            }
        }

        prevclassSeatNumber = seatsRowCounter;
        return localSeatsNamesList;
    }
}

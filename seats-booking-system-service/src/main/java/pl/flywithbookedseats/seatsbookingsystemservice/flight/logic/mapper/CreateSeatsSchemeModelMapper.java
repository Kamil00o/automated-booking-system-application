package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.mapper;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.SeatsSchemeModel;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

@Component
public class CreateSeatsSchemeModelMapper implements Function<CreateSeatsSchemeModelCommand, SeatsSchemeModel> {

    private static final String SEATS_FOLLOWING_LETTERS = "ABCDEFGHIJK";
    @Override
    public SeatsSchemeModel apply(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        return SeatsSchemeModel.builder()
                .planeModel(createSeatsSchemeModelCommand.planeModel())
                .seatsSchemeMap(seatsSchemeMapParser(createSeatsSchemeModelCommand))
                .build();
    }

    private HashMap<String, HashMap<String, Long>> seatsSchemeMapParser(
            CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {

        HashMap<String, HashMap<String, Long>> localSeatsSchemeMap = new HashMap<>();
        int iterationCounter = 0;
        for (String classType : createSeatsSchemeModelCommand.seatClassTypeList()) {
            localSeatsSchemeMap.put(classType,
                    generateSeatsSchemeModelMap(createSeatsSchemeModelCommand, iterationCounter));
            iterationCounter++;
        }
        return localSeatsSchemeMap;
    }

    private HashMap<String, Long> generateSeatsSchemeModelMap(
            CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand, int iterationCounter) {

        List<Integer> amountOfSeatsInARowList = createSeatsSchemeModelCommand.amountOfSeatsInARowPerSeatClassTypeList();
        List<Integer> amountOfRowsList = createSeatsSchemeModelCommand.amountOfRowsPerSeatClassTypeList();

        //if(amountOfRowsList.size() < amountOfRowsList.size())
        HashMap<String, Long> localSeatsSchemeModelMap = new HashMap<>();
        StringBuilder seatNumber = new StringBuilder();

        for (int i = 0; i < amountOfSeatsInARowList.get(iterationCounter); i++) {
            for (int j = 1; j < (amountOfRowsList.get(iterationCounter) + 1); j++) {
                seatNumber.append(j)
                          .append(SEATS_FOLLOWING_LETTERS.substring(i, i + 1));
                localSeatsSchemeModelMap.put(seatNumber.toString(), null);
                seatNumber.delete(0, seatNumber.length());
            }
        }
        return localSeatsSchemeModelMap;
    }
}

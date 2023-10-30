package pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.seatsschememodel;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;

import java.util.*;
import java.util.function.Function;

@Component
public class CreateSeatsSchemeModelMapper implements Function<CreateSeatsSchemeModelCommand, SeatsSchemeModel> {

    int prevclassSeatNumber;
    int savedSeatNumberAutoIncrementation = 0;
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

        TreeMap<String, String> sortedSeatsTreeMap = new TreeMap<String, String>(localSeatsSchemeMap);
        localSeatsSchemeMap.clear();
        return sortedSeatsTreeMap;
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
        List<Integer> seatNumbersToSkipList = createSeatsSchemeModelCommand.numbersOfExcludedSeatsList();
        StringBuilder seatNumber = new StringBuilder();
        int secondRowLetters = 2;
        int seatsRowCounter = prevclassSeatNumber;
        int seatNumberAutoIncrementation = 0;

        for (int i = 0; i < (amountOfSeatsInARowList.get(iterationCounter) * secondRowLetters); i++) {
            seatNumberAutoIncrementation = savedSeatNumberAutoIncrementation;
            for (int j = prevclassSeatNumber ; j <
                    (amountOfRowsList.get(iterationCounter) + prevclassSeatNumber); j++) {
                while (seatNumbersToSkipList.contains(j + seatNumberAutoIncrementation)) {
                    seatNumberAutoIncrementation++;
                }

                int updatedJ = j + seatNumberAutoIncrementation;
                seatNumber.append(updatedJ)
                        .append(SEATS_FOLLOWING_LETTERS.charAt(i));
                localSeatsNamesList.add(seatNumber.toString());
                seatNumber.delete(0, seatNumber.length());

                if (i == 0) {
                    seatsRowCounter++;
                }
            }
        }

        savedSeatNumberAutoIncrementation = seatNumberAutoIncrementation;
        prevclassSeatNumber = seatsRowCounter;
        return localSeatsNamesList;
    }
}

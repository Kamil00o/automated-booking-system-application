package pl.flywithbookedseats.api.seatsscheme;

import org.springframework.stereotype.Component;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntity;

import java.util.*;
import java.util.function.Function;

@Component
public class CreateSeatsSchemeModelMapper implements Function<CreateSeatsSchemeCommand, SeatsSchemeEntity> {

    int prevclassSeatNumber;
    int savedSeatNumberAutoIncrementation = 0;
    private static final String SEATS_FOLLOWING_LETTERS = "ABCDEFGHIJK";
    @Override
    public SeatsSchemeEntity apply(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        return SeatsSchemeEntity.builder()
                .planeModelName(createSeatsSchemeCommand.planeModelName())
                .seatsSchemeMap(seatsSchemeMapParser(createSeatsSchemeCommand))
                .build();
    }

    private Map<String, String> seatsSchemeMapParser(
            CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        Map<String, String> localSeatsSchemeMap = new HashMap<String, String>();
        List<String> seatsNamesList;
        int iterationCounter = 0;
        prevclassSeatNumber = 1;

        for (String classType : createSeatsSchemeCommand.seatClassTypeList()) {
            seatsNamesList = generateSeatsNames(createSeatsSchemeCommand, iterationCounter);
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

    private List<String> generateSeatsNames(CreateSeatsSchemeCommand createSeatsSchemeCommand,
                                            int iterationCounter) {
        List<String> localSeatsNamesList = new LinkedList<>();
        List<Integer> amountOfSeatsInARowList = createSeatsSchemeCommand.amountOfSeatsInARowPerSeatClassTypeList();
        List<Integer> amountOfRowsList = createSeatsSchemeCommand.amountOfRowsPerSeatClassTypeList();
        List<Integer> seatNumbersToSkipList = createSeatsSchemeCommand.numbersOfExcludedSeatsList();
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

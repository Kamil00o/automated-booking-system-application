package pl.flywithbookedseats.api.seatsscheme;

import java.util.*;

public class SeatsSchemeFactory {
    private static int prevclassSeatNumber;
    private static int savedSeatNumberAutoIncrementation = 0;
    private static final String SEATS_FOLLOWING_LETTERS = "ABCDEFGHIJK";

    public static Map<String, String> seatsSchemeMapParser(
            CreateSeatsSchemeCommand command) {
        Map<String, String> localSeatsSchemeMap = new HashMap<String, String>();
        List<String> seatsNamesList;
        int iterationCounter = 0;
        prevclassSeatNumber = 1;

        for (String classType : command.seatClassTypeList()) {
            seatsNamesList = generateSeatsNames(command, iterationCounter);
            generateSeatsSchemeModelMap(classType, localSeatsSchemeMap, seatsNamesList);
            iterationCounter++;
        }

        TreeMap<String, String> sortedSeatsTreeMap = new TreeMap<String, String>(localSeatsSchemeMap);
        localSeatsSchemeMap.clear();
        return sortedSeatsTreeMap;
    }

    private static Map<String, String> generateSeatsSchemeModelMap(String classTypeName,
                                                            Map<String, String> localSeatsSchemeMap,
                                                            List<String> localSeatsNamesList) {

        for (String seatName : localSeatsNamesList) {
            localSeatsSchemeMap.put(seatName, classTypeName);
        }
        return localSeatsSchemeMap;
    }

    private static List<String> generateSeatsNames(CreateSeatsSchemeCommand command,
                                            int iterationCounter) {
        List<String> localSeatsNamesList = new LinkedList<>();
        List<Integer> amountOfSeatsInARowList = command.amountOfSeatsInARowPerSeatClassTypeList();
        List<Integer> amountOfRowsList = command.amountOfRowsPerSeatClassTypeList();
        List<Integer> seatNumbersToSkipList = command.numbersOfExcludedSeatsList();
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

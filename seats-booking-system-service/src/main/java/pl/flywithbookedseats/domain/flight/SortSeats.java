package pl.flywithbookedseats.domain.flight;

import java.util.Comparator;

public class SortSeats implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (retrieveIntFromString(o1) - retrieveIntFromString(o2) == 0) {
            return o1.compareTo(o2);
        }
        return retrieveIntFromString(o1) - retrieveIntFromString(o2);
    }

    private int retrieveIntFromString(String stringValue) {
        String num = stringValue.replaceAll("\\D", "");
        return num.isEmpty() ? 0 : Integer.parseInt(num);
    }
}

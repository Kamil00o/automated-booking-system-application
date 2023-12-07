package pl.flywithbookedseats.domain.passenger;

public enum RequestType {

    UPDATE ("UPDATE"),
    DATA_REQUEST("DATA REQUEST");

    private final String value;

    RequestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

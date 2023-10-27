package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.flight;

public class FlightConstImpl {

    public static final String FLIGHT_ALREADY_EXISTS_FLIGHT_NAME =
            "Flight entity for %s flight name already exists !!!";
    public static final String SEATS_SCHEME_NOT_FOUND_FLIGHT_NOT_CREATED_EXCEPTION =
            "Seats scheme model for specified %s plane type does not exist! Flight %s hasn't been created !!!";
    public static final String FLIGHT_DATABASE_IS_EMPTY_EXCEPTION =
            "Flight Database is empty!!!";
    public static final String FLIGHTS_NOT_RETRIEVED =
            "Flights haven't been retrieved!";
    public static final String FLIGHT_NOT_FOUND_FLIGHT_NAME =
            "Flight with %s flight name has not been found!!!";
    public static final String FLIGHT_NOT_FOUND_FLIGHT_SERVICE_ID =
            "Flight with %s flight service ID has not been found!!!";
}

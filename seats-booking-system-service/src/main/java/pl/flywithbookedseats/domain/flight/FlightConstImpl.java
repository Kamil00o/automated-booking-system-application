package pl.flywithbookedseats.domain.flight;

public class FlightConstImpl {

    public static final String FLIGHT_ALREADY_EXISTS_FLIGHT_NAME =
            "Flight entity for %s flight name already exists !!!";
    public static final String FLIGHT_ALREADY_EXISTS_FLIGHT_SERVICE_ID =
            "Flight entity for %s flight service ID already exists !!!";
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
    public static final String FLIGHT_NOT_UPDATED =
            "Flight %s has not been UPDATED !!!";
    public static final String FLIGHT_UPDATED =
            "Flight %s has been updated";
    public static final String FLIGHT_REMOVED_ALL =
            "All Flights have been removed from database !";
    public static final String FLIGHT_REMOVED_NAME =
            "Flight with %s flight name has been removed from database!";
    public static final String FLIGHT_REMOVED_SERVICE_ID =
            "Flight with %s flight service ID has been removed from database!";
    public static final String NO_SEATS_AVAILABLE =
            "no seats available";
    public static final String NO_SEATS_AVAILABLE_MSG =
            "There are no seats available for flight !!!";

    public static final String RESERVATION_NOT_MADE_FULL_FLIGHT =
            "Reservatiom has not been made due to the lack of free seats in the plane!";
}

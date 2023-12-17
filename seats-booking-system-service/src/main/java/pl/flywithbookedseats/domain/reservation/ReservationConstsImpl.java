package pl.flywithbookedseats.domain.reservation;

public class ReservationConstsImpl {

    public static final String RESERVATION_ADDED_PASSENGER =
            "ReservationEntity with passenger entity has been added to database.";
    public static final String RESERVATION_ADDED_NO_PASSENGER =
            "ReservationEntity without passenger entity has been added to database.";
    public static final String RESERVATION_NOT_FOUND_ID =
            "ReservationEntity with ID: %s has not been found!";
    public static final String RESERVATION_NOT_FOUND_EMAIL =
            "Reservations for email: %s has not been found!";
    public static final String RESERVATION_NOT_FOUND_SEAT_NUMBER =
            "Reservations for seat number: %s has not been found!";
    public static final String RESERVATION_ALREADY_EXISTS_SEAT_NUMBER =
            "ReservationEntity with seat number: %s already exists!";
    public static final String RESERVATION_NOT_UPDATED =
            "ReservationEntity with ID: %s has not been updated!!";
    public static final String RESERVATION_DATABASE_IS_EMPTY_EXCEPTION =
            "ReservationEntity Database is empty!!!";
    public static final String RESERVATIONS_NOT_RETRIEVED =
            "Reservations haven't been retrieved!";
    public static final String RESERVATION_NOT_DELETED_ID =
            "ReservationEntity for ID: %s has not been deleted!";
    public static final String REMOVING_RESERVATION_COMPLETE =
            "Specified Reservations have been deleted!";
    public static final String RESERVATION_NOT_CREATED =
            "ReservationEntity has not been created!";
}

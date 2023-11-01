package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.reservation;

public class ReservationConstsImpl {

    public static final String RESERVATION_ADDED_PASSENGER =
            "Reservation with passenger entity has been added to database.";
    public static final String RESERVATION_ADDED_NO_PASSENGER =
            "Reservation without passenger entity has been added to database.";
    public static final String RESERVATION_NOT_FOUND_ID =
            "Reservation with ID: %s has not been found!";
    public static final String RESERVATION_NOT_FOUND_EMAIL =
            "Reservation with for email: %s has not been found!";
    public static final String RESERVATION_ALREADY_EXISTS_SEAT_NUMBER =
            "Reservation with seat number: %s already exists!";
    public static final String RESERVATION_NOT_UPDATED =
            "Reservation with ID: %s has not been updated!!";
    public static final String RESERVATION_DATABASE_IS_EMPTY_EXCEPTION =
            "Reservation Database is empty!!!";
    public static final String RESERVATIONS_NOT_RETRIEVED =
            "Reservations haven't been retrieved!";
    public static final String RESERVATION_NOT_DELETED_ID =
            "Reservation for ID: %s has not been deleted!";
    public static final String REMOVING_RESERVATION_COMPLETE =
            "Specified Reservations have been deleted!";
    public static final String RESERVATION_NOT_CREATED =
            "Reservation has not been created!";
}

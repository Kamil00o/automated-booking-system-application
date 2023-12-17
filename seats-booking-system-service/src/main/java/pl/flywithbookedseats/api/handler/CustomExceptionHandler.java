package pl.flywithbookedseats.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.flywithbookedseats.api.response.ErrorResponse;
import pl.flywithbookedseats.domain.flight.*;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeAlreadyExistsException;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SeatsSchemeAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleSeatsSchemeAlreadyExistsException(
            SeatsSchemeAlreadyExistsException exception
    ) {
        return buildResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SeatsSchemeNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleSeatsSchemeNotFoundException(
            SeatsSchemeNotFoundException exception
    ) {
        return buildResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleFlightAlreadyExistsException(
            FlightAlreadyExistsException exception
    ) {
        return buildResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleFlightNotFoundException(
            FlightNotFoundException exception
    ) {
        return buildResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FlightNotCreatedException.class)
    public final ResponseEntity<ErrorResponse> handleFlightNotCreatedException(
            FlightNotCreatedException exception
    ) {
        return buildResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FlightDatabaseIsEmptyException.class)
    public final ResponseEntity<ErrorResponse> handleFlightDatabaseIsEmptyException(
            FlightDatabaseIsEmptyException exception
    ) {
        return buildResponse(exception, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FullFlightException.class)
    public final ResponseEntity<ErrorResponse> handleFullFlightException(
            FullFlightException exception
    ) {
        return buildResponse(exception, HttpStatus.CONFLICT);
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }
}

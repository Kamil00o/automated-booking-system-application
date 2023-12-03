package pl.flywithbookedseats.passengeraccountservice.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.flywithbookedseats.passengeraccountservice.api.response.ErrorResponse;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerAlreadyExistsException;
import pl.flywithbookedseats.passengeraccountservice.domain.passenger.PassengerNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PassengerNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlePassengerNotFoundException(
            PassengerNotFoundException exception) {
        return buildResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PassengerAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handlePassengerAlreadyExistsException(
            PassengerAlreadyExistsException exception) {
        return buildResponse(exception, HttpStatus.CONFLICT);
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }
}

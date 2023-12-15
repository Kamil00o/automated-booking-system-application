package pl.flywithbookedseats.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.flywithbookedseats.api.response.ErrorResponse;
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

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }
}

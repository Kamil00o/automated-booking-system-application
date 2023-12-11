package pl.flywithbookedseats.domain.seatsscheme;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class SeatsSchemeModelAlreadyExistsException extends BadRequestException {
    public SeatsSchemeModelAlreadyExistsException(String message) {
        super(message);
    }
}

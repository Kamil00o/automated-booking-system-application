package pl.flywithbookedseats.domain.seatsscheme;

import pl.flywithbookedseats.logic.exceptions.BadRequestException;

public class SeatsSchemeModelNotFoundException extends BadRequestException {
    public SeatsSchemeModelNotFoundException(String message) {
        super(message);
    }
}

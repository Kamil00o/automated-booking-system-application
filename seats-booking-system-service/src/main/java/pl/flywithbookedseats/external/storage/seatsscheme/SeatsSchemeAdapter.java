package pl.flywithbookedseats.external.storage.seatsscheme;

import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;

public class SeatsSchemeAdapter implements SeatsSchemeRepository {
    @Override
    public SeatsScheme save(SeatsScheme seatsScheme) {
        return null;
    }

    @Override
    public boolean existsByPlaneModelName(String planeModelName) {
        return false;
    }
}

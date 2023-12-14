package pl.flywithbookedseats.domain.seatsscheme;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeatsSchemeService {

    private final SeatsSchemeRepository repository;

    public SeatsScheme addNewSeatsSchemeModel(SeatsScheme seatsScheme) {
        if (!exists(seatsScheme)) {
            return repository.save(seatsScheme);
        } else {
            String planeModelName = seatsScheme.getPlaneModelName();
            throw new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION
                    .formatted(planeModelName));
        }
    }

    private boolean exists(SeatsScheme seatsScheme) {
        return repository.existsByPlaneModelName(seatsScheme.getPlaneModelName());
    }
}

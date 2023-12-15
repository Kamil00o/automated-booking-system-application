package pl.flywithbookedseats.domain.seatsscheme;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import pl.flywithbookedseats.api.seatsscheme.SeatsSchemeDto;

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

    public SeatsScheme retrieveSeatsSchemeModelByPlaneModel(String planeModelName) {
        return repository.findByPlaneModelName(planeModelName);
    }

    public SeatsScheme retrieveSeatsSchemeModelById(Long id) {
        return repository.findById(id);
    }

    public SeatsScheme updateSeatsSchemeById(Long id, SeatsScheme seatsScheme) {
        SeatsScheme savedSeatsScheme = retrieveSeatsSchemeModelById(id);
        return updateSpecifiedSeatsScheme(savedSeatsScheme, seatsScheme);
    }

    private SeatsScheme updateSpecifiedSeatsScheme(SeatsScheme seatsSchemeToUpdate,
                                                   SeatsScheme seatsSchemeUpdateData) {
        String planeModelName = seatsSchemeUpdateData.getPlaneModelName();
        //TODO: Exists method to improve!
        if (!exists(seatsSchemeToUpdate)) {
            seatsSchemeToUpdate.setPlaneModelName(planeModelName);
            return repository.save(seatsSchemeToUpdate);
        } else {
            throw new SeatsSchemeModelAlreadyExistsException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION
                    .formatted(planeModelName));
        }
    }

    private boolean exists(SeatsScheme seatsScheme) {
        return repository.existsByPlaneModelName(seatsScheme.getPlaneModelName());
    }
}

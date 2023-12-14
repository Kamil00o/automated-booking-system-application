package pl.flywithbookedseats.external.storage.seatsscheme;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.domain.seatsscheme.ConstsImpl;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeModelNotFoundException;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeRepository;

@RequiredArgsConstructor
public class SeatsSchemeStorageAdapter implements SeatsSchemeRepository {

    private final JpaSeatsSchemeRepository repository;
    private final JpaSeatsSchemeRepositoryMapper mapper;

    @Override
    public SeatsScheme save(SeatsScheme seatsScheme) {
        SeatsSchemeEntity savedSeatsSchemeEntity = repository.save(mapper.toEntity(seatsScheme));
        return mapper.toDomain(savedSeatsSchemeEntity);
    }

    @Override
    public boolean existsByPlaneModelName(String planeModelName) {
        return repository.existsByPlaneModelName(planeModelName);
    }

    @Override
    public SeatsScheme findByPlaneModelName(String planeModelName) {
        SeatsSchemeEntity savedSeatsSchemeEntity = repository.findByPlaneModelName(planeModelName)
                .orElseThrow(() ->
                        new SeatsSchemeModelNotFoundException(
                                ConstsImpl
                                        .SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME.formatted(planeModelName)));

        return mapper.toDomain(savedSeatsSchemeEntity);
    }
}

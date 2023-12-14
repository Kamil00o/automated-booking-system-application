package pl.flywithbookedseats.external.storage.seatsscheme;

import lombok.RequiredArgsConstructor;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
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
}

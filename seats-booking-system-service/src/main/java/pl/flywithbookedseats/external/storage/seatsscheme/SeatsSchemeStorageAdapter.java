package pl.flywithbookedseats.external.storage.seatsscheme;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.flywithbookedseats.domain.seatsscheme.*;

import java.util.List;
import java.util.stream.Collectors;

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
                        new SeatsSchemeNotFoundException(
                                ConstsImpl
                                        .SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME.formatted(planeModelName)));

        return mapper.toDomain(savedSeatsSchemeEntity);
    }

    @Override
    public SeatsScheme findById(Long id) {
        SeatsSchemeEntity savedSeatsSchemeEntity = repository.findById(id)
                .orElseThrow(() -> new SeatsSchemeNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        return mapper.toDomain(savedSeatsSchemeEntity);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteByPlaneModelName(String planeModelName) {
        repository.deleteByPlaneModelName(planeModelName);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public PageSeatsScheme findAll(Pageable pageable) {
        Page<SeatsSchemeEntity> pageOfSeatsSchemeEntities = repository.findAll(pageable);
        List<SeatsScheme> seatsSchemesOnCurrentPage = pageOfSeatsSchemeEntities
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());

        return new PageSeatsScheme(seatsSchemesOnCurrentPage,
                pageable.getPageNumber() + 1,
                pageOfSeatsSchemeEntities.getTotalPages(),
                pageOfSeatsSchemeEntities.getTotalElements());
    }
}

package pl.flywithbookedseats.domain.seatsscheme;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsscheme.*;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntity;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;
import pl.flywithbookedseats.external.storage.seatsscheme.JpaSeatsSchemeRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsSchemeModelService implements SeatsSchemeModelServiceInt {

    private static final Logger log = LoggerFactory.getLogger(SeatsSchemeModelService.class);

    private final JpaSeatsSchemeRepository jpaSeatsSchemeRepository;
    private final CreateSeatsSchemeModelMapper createSeatsSchemeModelMapper;
    private final SeatsSchemeModelDtoMapper seatsSchemeModelDtoMapper;
    private final CreateSeatsSchemeCommandMapper createSeatsSchemeCommandMapper;

    @Transactional
    @Override
    public void addNewSeatsSchemeModel(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        if (!exists(createSeatsSchemeCommand)) {
            SeatsSchemeEntity seatsSchemeEntity = createSeatsSchemeModelMapper.apply(createSeatsSchemeCommand);
            jpaSeatsSchemeRepository.save(seatsSchemeEntity);
        } else {
            String planeModelName = createSeatsSchemeCommand.planeModelName();
            throw new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION.formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        List<SeatsSchemeEntity> seatsSchemeEntityList = jpaSeatsSchemeRepository.findAll();
        List<SeatsSchemeEntityDto> seatsSchemeEntityDtoList = new LinkedList<>();
        seatsSchemeEntityList.forEach(seatsSchemeModel -> seatsSchemeEntityDtoList
                .add(seatsSchemeModelDtoMapper.apply(seatsSchemeModel)));

        return seatsSchemeEntityDtoList;
    }

    @Override
    public SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModelName) {
        SeatsSchemeEntity savedSeatsSchemeEntity = jpaSeatsSchemeRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() ->
                        new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                                .formatted(planeModelName)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
    }

    @Override
    public SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id) {
        SeatsSchemeEntity savedSeatsSchemeEntity = jpaSeatsSchemeRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
    }

    @Transactional
    @Override
    public SeatsSchemeEntityDto updateSeatsSchemeModel(Long id,
                                                       UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        String planeModelName = updateSeatsSchemeCommand.planeModelName();
        SeatsSchemeEntity savedSeatsSchemeEntity = jpaSeatsSchemeRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        if (!exists(updateSeatsSchemeCommand)) {
            savedSeatsSchemeEntity.setPlaneModelName(planeModelName);
            jpaSeatsSchemeRepository.saveAndFlush(savedSeatsSchemeEntity);
            return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
        } else {
            throw new SeatsSchemeModelAlreadyExistsException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION
                    .formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelById(Long id) {
        SeatsSchemeEntity savedSeatsSchemeEntity = jpaSeatsSchemeRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));
        jpaSeatsSchemeRepository.delete(savedSeatsSchemeEntity);
        log.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_ID.formatted(id));
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelByPlaneModelName(String planeModelName) {
        SeatsSchemeEntity savedSeatsSchemeEntity = jpaSeatsSchemeRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                        .formatted(planeModelName)));
        jpaSeatsSchemeRepository.delete(savedSeatsSchemeEntity);
        log.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_PLANE_NAME.formatted(planeModelName));
    }

    @Transactional
    @Override
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        jpaSeatsSchemeRepository.deleteAll();
        log.info(ConstsImpl.SEATS_SCHEME_MODEL_ALL_REMOVED);
    }

    private boolean exists(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        return jpaSeatsSchemeRepository.existsByPlaneModelName(createSeatsSchemeCommand.planeModelName());
    }

    private boolean exists(UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        return jpaSeatsSchemeRepository.existsByPlaneModelName(updateSeatsSchemeCommand.planeModelName());
    }
}

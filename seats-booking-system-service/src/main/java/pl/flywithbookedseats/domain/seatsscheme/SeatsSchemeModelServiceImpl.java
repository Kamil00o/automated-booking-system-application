package pl.flywithbookedseats.domain.seatsscheme;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsscheme.*;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntity;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeModelRepository;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsSchemeModelServiceImpl implements SeatsSchemeModelService {

    private static final Logger logger = LoggerFactory.getLogger(SeatsSchemeModelServiceImpl.class);

    private final SeatsSchemeModelRepository seatsSchemeModelRepository;
    private final CreateSeatsSchemeModelMapper createSeatsSchemeModelMapper;
    private final SeatsSchemeModelDtoMapper seatsSchemeModelDtoMapper;
    private final CreateSeatsSchemeCommandMapper createSeatsSchemeCommandMapper;

    @Transactional
    @Override
    public void addNewSeatsSchemeModel(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        if (!exists(createSeatsSchemeCommand)) {
            SeatsSchemeEntity seatsSchemeEntity = createSeatsSchemeModelMapper.apply(createSeatsSchemeCommand);
            seatsSchemeModelRepository.save(seatsSchemeEntity);
        } else {
            String planeModelName = createSeatsSchemeCommand.planeModelName();
            throw new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION.formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        List<SeatsSchemeEntity> seatsSchemeEntityList = seatsSchemeModelRepository.findAll();
        List<SeatsSchemeEntityDto> seatsSchemeEntityDtoList = new LinkedList<>();
        seatsSchemeEntityList.forEach(seatsSchemeModel -> seatsSchemeEntityDtoList
                .add(seatsSchemeModelDtoMapper.apply(seatsSchemeModel)));

        return seatsSchemeEntityDtoList;
    }

    @Override
    public SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModelName) {
        SeatsSchemeEntity savedSeatsSchemeEntity = seatsSchemeModelRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() ->
                        new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                                .formatted(planeModelName)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
    }

    @Override
    public SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id) {
        SeatsSchemeEntity savedSeatsSchemeEntity = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
    }

    @Transactional
    @Override
    public SeatsSchemeEntityDto updateSeatsSchemeModel(Long id,
                                                       UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        String planeModelName = updateSeatsSchemeCommand.planeModelName();
        SeatsSchemeEntity savedSeatsSchemeEntity = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        if (!exists(updateSeatsSchemeCommand)) {
            savedSeatsSchemeEntity.setPlaneModelName(planeModelName);
            seatsSchemeModelRepository.saveAndFlush(savedSeatsSchemeEntity);
            return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeEntity);
        } else {
            throw new SeatsSchemeModelAlreadyExistsException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION
                    .formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelById(Long id) {
        SeatsSchemeEntity savedSeatsSchemeEntity = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));
        seatsSchemeModelRepository.delete(savedSeatsSchemeEntity);
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_ID.formatted(id));
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelByPlaneModelName(String planeModelName) {
        SeatsSchemeEntity savedSeatsSchemeEntity = seatsSchemeModelRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                        .formatted(planeModelName)));
        seatsSchemeModelRepository.delete(savedSeatsSchemeEntity);
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_PLANE_NAME.formatted(planeModelName));
    }

    @Transactional
    @Override
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        seatsSchemeModelRepository.deleteAll();
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_ALL_REMOVED);
    }

    private boolean exists(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        return seatsSchemeModelRepository.existsByPlaneModelName(createSeatsSchemeCommand.planeModelName());
    }

    private boolean exists(UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        return seatsSchemeModelRepository.existsByPlaneModelName(updateSeatsSchemeCommand.planeModelName());
    }
}

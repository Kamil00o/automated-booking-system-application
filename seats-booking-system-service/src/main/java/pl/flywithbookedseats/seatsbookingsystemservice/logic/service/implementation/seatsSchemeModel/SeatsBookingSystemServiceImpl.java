package pl.flywithbookedseats.seatsbookingsystemservice.logic.service.implementation.seatsSchemeModel;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.SeatsSchemeModelAlreadyExistsException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.exceptions.SeatsSchemeModelNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.CreateSeatsSchemeModelMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.mapper.SeatsSchemeModelDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.command.seatsschememodel.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.model.dto.SeatsSchemeModelDto;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.repository.SeatsSchemeModelRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.logic.service.SeatsBookingSystemService;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsBookingSystemServiceImpl implements SeatsBookingSystemService {

    private static final Logger logger = LoggerFactory.getLogger(SeatsBookingSystemService.class);

    private final SeatsSchemeModelRepository seatsSchemeModelRepository;
    private final CreateSeatsSchemeModelMapper createSeatsSchemeModelMapper;
    private final SeatsSchemeModelDtoMapper seatsSchemeModelDtoMapper;

    @Transactional
    @Override
    public void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        if (!exists(createSeatsSchemeModelCommand)) {
            SeatsSchemeModel seatsSchemeModel = createSeatsSchemeModelMapper.apply(createSeatsSchemeModelCommand);
            seatsSchemeModelRepository.save(seatsSchemeModel);
        } else {
            String planeModelName = createSeatsSchemeModelCommand.planeModelName();
            throw new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION.formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        List<SeatsSchemeModel> seatsSchemeModelList = seatsSchemeModelRepository.findAll();
        List<SeatsSchemeModelDto> seatsSchemeModelDtoList = new LinkedList<>();
        seatsSchemeModelList.forEach(seatsSchemeModel -> seatsSchemeModelDtoList
                .add(seatsSchemeModelDtoMapper.apply(seatsSchemeModel)));

        return seatsSchemeModelDtoList;
    }

    @Override
    public SeatsSchemeModelDto retrieveSeatsSchemeModelByPlaneModel(String planeModelName) {
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() ->
                        new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                                .formatted(planeModelName)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeModel);
    }

    @Override
    public SeatsSchemeModelDto retrieveSeatsSchemeModelById(Long id) {
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeModel);
    }

    @Transactional
    @Override
    public SeatsSchemeModelDto updateSeatsSchemeModel(Long id,
                                                      UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand) {
        String planeModelName = updateSeatsSchemeModelCommand.planeModelName();
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        if (!exists(updateSeatsSchemeModelCommand)) {
            savedSeatsSchemeModel.setPlaneModelName(planeModelName);
            seatsSchemeModelRepository.saveAndFlush(savedSeatsSchemeModel);
            return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeModel);
        } else {
            throw new SeatsSchemeModelAlreadyExistsException(ConstsImpl.SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION
                    .formatted(planeModelName));
        }
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelById(Long id) {
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));
        seatsSchemeModelRepository.delete(savedSeatsSchemeModel);
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_ID.formatted(id));
    }

    @Transactional
    @Override
    public void deleteSeatsSchemeModelByPlaneModelName(String planeModelName) {
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findByPlaneModelName(planeModelName)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(ConstsImpl.SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                        .formatted(planeModelName)));
        seatsSchemeModelRepository.delete(savedSeatsSchemeModel);
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_REMOVAL_PLANE_NAME.formatted(planeModelName));
    }

    @Transactional
    @Override
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        seatsSchemeModelRepository.deleteAll();
        logger.info(ConstsImpl.SEATS_SCHEME_MODEL_ALL_REMOVED);
    }

    private boolean exists(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        return seatsSchemeModelRepository.existsByPlaneModelName(createSeatsSchemeModelCommand.planeModelName());
    }

    private boolean exists(UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand) {
        return seatsSchemeModelRepository.existsByPlaneModelName(updateSeatsSchemeModelCommand.planeModelName());
    }
}

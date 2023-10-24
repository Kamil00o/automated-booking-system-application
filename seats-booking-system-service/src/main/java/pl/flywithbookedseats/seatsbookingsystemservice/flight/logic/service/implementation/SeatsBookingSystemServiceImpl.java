package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.exceptions.SeatsSchemeModelNotFoundException;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.mapper.CreateSeatsSchemeModelMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.mapper.SeatsSchemeModelDtoMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto.SeatsSchemeModelDto;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.repository.SeatsSchemeModelRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service.SeatsBookingSystemService;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatsBookingSystemServiceImpl implements SeatsBookingSystemService {

    private static final String SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME =
            "Seats scheme model with specified plane model name: %s has not been found!!!";
    private static final String SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID =
            "Seats scheme model with specified ID: %s has not been found!!!";
    private static final String SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION =
            "Seats scheme model for %s plane model name already exists !!!";

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
            throw new SeatsSchemeModelNotFoundException(SEATS_SCHEME_ALREADY_EXISTS_EXCEPTION.formatted(planeModelName));
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
                        new SeatsSchemeModelNotFoundException(SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_PLANE_NAME
                                .formatted(planeModelName)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeModel);
    }

    @Override
    public SeatsSchemeModelDto retrieveSeatsSchemeModelById(Long id) {
        SeatsSchemeModel savedSeatsSchemeModel = seatsSchemeModelRepository.findById(id)
                .orElseThrow(() -> new SeatsSchemeModelNotFoundException(SEATS_SCHEME_MODEL_NOT_FOUND_EXCEPTION_ID
                        .formatted(id)));

        return seatsSchemeModelDtoMapper.apply(savedSeatsSchemeModel);
    }

    @Transactional
    @Override
    public SeatsSchemeModelDto updateSeatsSchemeModel(Long id) {
        return null;
    }

    private boolean exists(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        return seatsSchemeModelRepository.existsByPlaneModelName(createSeatsSchemeModelCommand.planeModelName());
    }
}

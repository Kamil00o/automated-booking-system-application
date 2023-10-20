package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.mapper.CreateSeatsSchemeModelMapper;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.SeatsSchemeModel;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto.SeatsSchemeModelDto;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.repository.SeatsSchemeModelRepository;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service.SeatsBookingSystemService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeatsBookingSystemServiceImpl implements SeatsBookingSystemService {

    private final SeatsSchemeModelRepository seatsSchemeModelRepository;
    private final CreateSeatsSchemeModelMapper createSeatsSchemeModelMapper;

    @Transactional
    @Override
    public void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        if (!exists(createSeatsSchemeModelCommand)) {
            SeatsSchemeModel seatsSchemeModel = createSeatsSchemeModelMapper.apply(createSeatsSchemeModelCommand);
            seatsSchemeModelRepository.save(seatsSchemeModel);
        }
    }

    @Transactional
    @Override
    public List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb() {

        return null;
    }

    @Override
    public Optional<SeatsSchemeModelDto> retrieveSeatsSchemeModelByPlaneModel(String planeModel) {
        return null;
    }

    @Override
    public Optional<SeatsSchemeModelDto> retrieveSeatsSchemeModelById(Long id) {
        return null;
    }

    @Transactional
    @Override
    public SeatsSchemeModelDto updateSeatsSchemeModel(Long id) {
        return null;
    }

    private boolean exists(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand) {
        return seatsSchemeModelRepository.existsByPlaneModel(createSeatsSchemeModelCommand.planeModel());
    }
}
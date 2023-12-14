package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsscheme.CreateSeatsSchemeCommand;
import pl.flywithbookedseats.api.seatsscheme.UpdateSeatsSchemeCommand;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeModelService;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class SeatsSchemeApplicationService {

    private final SeatsSchemeModelService service;

    @Transactional
    public void addNewSeatsSchemeModel(CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        service.addNewSeatsSchemeModel(createSeatsSchemeCommand);
    }

    public List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        return service.retrieveAllSavedSeatsSchemeModelsFromDb();
    }

    public SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModel) {
        return service.retrieveSeatsSchemeModelByPlaneModel(planeModel);
    }

    public SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id) {
        return service.retrieveSeatsSchemeModelById(id);
    }

    @Transactional
    public SeatsSchemeEntityDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        return service.updateSeatsSchemeModel(id, updateSeatsSchemeCommand);
    }

    @Transactional
    public void deleteSeatsSchemeModelById(Long id) {
        service.deleteSeatsSchemeModelById(id);
    }

    @Transactional
    public void deleteSeatsSchemeModelByPlaneModelName(String planeModelName) {
        service.deleteSeatsSchemeModelByPlaneModelName(planeModelName);
    }

    @Transactional
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        service.deleteAllSavedSeatsSchemeModelsFromDb();
    }
}

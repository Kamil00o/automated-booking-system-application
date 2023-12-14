package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.api.seatsscheme.UpdateSeatsSchemeCommand;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeModelService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatsSchemeApplicationService {

    private final SeatsSchemeModelService service121212;
    private final SeatsSchemeService service;

    @Transactional
    public SeatsScheme addNewSeatsSchemeModel(SeatsScheme seatsScheme) {
        return service.addNewSeatsSchemeModel(seatsScheme);
    }

    public List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        return service121212.retrieveAllSavedSeatsSchemeModelsFromDb();
    }

    public SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModel) {
        return service121212.retrieveSeatsSchemeModelByPlaneModel(planeModel);
    }

    public SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id) {
        return service121212.retrieveSeatsSchemeModelById(id);
    }

    @Transactional
    public SeatsSchemeEntityDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        return service121212.updateSeatsSchemeModel(id, updateSeatsSchemeCommand);
    }

    @Transactional
    public void deleteSeatsSchemeModelById(Long id) {
        service121212.deleteSeatsSchemeModelById(id);
    }

    @Transactional
    public void deleteSeatsSchemeModelByPlaneModelName(String planeModelName) {
        service121212.deleteSeatsSchemeModelByPlaneModelName(planeModelName);
    }

    @Transactional
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        service121212.deleteAllSavedSeatsSchemeModelsFromDb();
    }
}

package pl.flywithbookedseats.appservices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.domain.seatsscheme.PageSeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeService;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatsSchemeApplicationService {

    private final SeatsSchemeService service;

    @Transactional
    public SeatsScheme addNewSeatsSchemeModel(SeatsScheme seatsScheme) {
        return service.addNewSeatsSchemeModel(seatsScheme);
    }

    public PageSeatsScheme retrieveAllSavedSeatsSchemeModelsFromDb(Pageable pageable) {
        return service.retrieveAllSavedSeatsSchemeModelsFromDb(pageable);
    }

    public SeatsScheme retrieveSeatsSchemeModelByPlaneModel(String planeModel) {
        return service.retrieveSeatsSchemeModelByPlaneModel(planeModel);
    }

    public SeatsScheme retrieveSeatsSchemeModelById(Long id) {
        return service.retrieveSeatsSchemeModelById(id);
    }

    @Transactional
    public SeatsScheme updateSeatsSchemeModel(Long id, SeatsScheme seatsScheme) {
        return service.updateSeatsSchemeById(id, seatsScheme);
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

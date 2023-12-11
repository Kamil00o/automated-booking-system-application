package pl.flywithbookedseats.domain.seatsscheme;

import pl.flywithbookedseats.api.seatsscheme.CreateSeatsSchemeCommand;
import pl.flywithbookedseats.api.seatsscheme.UpdateSeatsSchemeCommand;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.List;

public interface SeatsSchemeModelService {

    void addNewSeatsSchemeModel(CreateSeatsSchemeCommand createSeatsSchemeCommand);

    List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb();

    SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModel);

    SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id);

    SeatsSchemeEntityDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeCommand updateSeatsSchemeCommand);

    void deleteSeatsSchemeModelById(Long id);

    void deleteSeatsSchemeModelByPlaneModelName(String planeModelName);

    void deleteAllSavedSeatsSchemeModelsFromDb();
}

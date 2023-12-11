package pl.flywithbookedseats.domain.seatsscheme;

import pl.flywithbookedseats.api.seatsscheme.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.api.seatsscheme.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.List;

public interface SeatsSchemeModelService {

    void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand);

    List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb();

    SeatsSchemeEntityDto retrieveSeatsSchemeModelByPlaneModel(String planeModel);

    SeatsSchemeEntityDto retrieveSeatsSchemeModelById(Long id);

    SeatsSchemeEntityDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand);

    void deleteSeatsSchemeModelById(Long id);

    void deleteSeatsSchemeModelByPlaneModelName(String planeModelName);

    void deleteAllSavedSeatsSchemeModelsFromDb();
}

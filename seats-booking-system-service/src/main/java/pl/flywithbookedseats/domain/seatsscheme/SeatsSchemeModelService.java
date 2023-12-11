package pl.flywithbookedseats.domain.seatsscheme;

import pl.flywithbookedseats.api.seatsscheme.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.api.seatsscheme.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeModelDto;

import java.util.List;

public interface SeatsSchemeModelService {

    void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand);

    List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb();

    SeatsSchemeModelDto retrieveSeatsSchemeModelByPlaneModel(String planeModel);

    SeatsSchemeModelDto retrieveSeatsSchemeModelById(Long id);

    SeatsSchemeModelDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand);

    void deleteSeatsSchemeModelById(Long id);

    void deleteSeatsSchemeModelByPlaneModelName(String planeModelName);

    void deleteAllSavedSeatsSchemeModelsFromDb();
}

package pl.flywithbookedseats.logic.service;

import pl.flywithbookedseats.logic.model.command.seatsschememodel.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.logic.model.command.seatsschememodel.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.logic.model.dto.SeatsSchemeModelDto;

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

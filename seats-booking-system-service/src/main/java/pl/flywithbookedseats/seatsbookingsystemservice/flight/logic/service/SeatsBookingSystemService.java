package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service;

import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.UpdateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto.SeatsSchemeModelDto;

import java.util.List;
import java.util.Optional;

public interface SeatsBookingSystemService {

    void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand);

    List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb();

    SeatsSchemeModelDto retrieveSeatsSchemeModelByPlaneModel(String planeModel);

    SeatsSchemeModelDto retrieveSeatsSchemeModelById(Long id);

    SeatsSchemeModelDto updateSeatsSchemeModel(Long id, UpdateSeatsSchemeModelCommand updateSeatsSchemeModelCommand);

    void deleteSeatsSchemeModelById(Long id);

    void deleteSeatsSchemeModelByPlaneModelName(String planeModelName);

    void deleteAllSavedSeatsSchemeModelsFromDb();
}

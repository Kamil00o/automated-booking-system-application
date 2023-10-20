package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.service;

import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.command.CreateSeatsSchemeModelCommand;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.dto.SeatsSchemeModelDto;

import java.util.List;
import java.util.Optional;

public interface SeatsBookingSystemService {

    void addNewSeatsSchemeModel(CreateSeatsSchemeModelCommand createSeatsSchemeModelCommand);

    List<SeatsSchemeModelDto> retrieveAllSavedSeatsSchemeModelsFromDb();

    Optional<SeatsSchemeModelDto> retrieveSeatsSchemeModelByPlaneModel(String planeModel);

    Optional<SeatsSchemeModelDto> retrieveSeatsSchemeModelById(Long id);

    SeatsSchemeModelDto updateSeatsSchemeModel(Long id);
}

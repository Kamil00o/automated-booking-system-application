package pl.flywithbookedseats.api.seatsscheme;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.SeatsSchemeApplicationService;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeModelService;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeEntityDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/seats-booking")
public class SeatsSchemeController {

    private final SeatsSchemeModelService seatsSchemeModelServiceImpl;
    private final SeatsSchemeApplicationService service;
    private final CreateSeatsSchemeCommandMapper commandMapper;
    private final SeatsSchemeDtoMapper mapper;

    @PostMapping
    public SeatsSchemeDto addNewSeatsSchemeModel(
            @Valid @RequestBody CreateSeatsSchemeCommand createSeatsSchemeCommand) {
        String planeModelName = createSeatsSchemeCommand.planeModelName();
        log.info("Adding new seats scheme to database for {} plane model.", planeModelName);
        SeatsScheme savedSeatsScheme = service
                .addNewSeatsSchemeModel(commandMapper.toDomain(createSeatsSchemeCommand));
        log.info("Seats scheme for {} added successfully!!", planeModelName);
        return mapper.toDto(savedSeatsScheme);
    }

    @GetMapping(path = "/planeModelName/{planeModelName}")
    public SeatsSchemeDto retrieveSeatsSchemeModelByPlaneModel(@PathVariable String planeModelName) {
        log.info("Retrieving seat scheme model data for {} plane model.", planeModelName);
        SeatsScheme retrievedSavedSeatsScheme = service.retrieveSeatsSchemeModelByPlaneModel(planeModelName);
        return mapper.toDto(retrievedSavedSeatsScheme);
    }

    @GetMapping(path = "/id/{id}")
    public SeatsSchemeDto retrieveSeatsSchemeModelById(@PathVariable Long id) {
        log.info("Retrieving seat scheme model data for plane model with id {}.", id);
        SeatsScheme retrievedSavedSeatsScheme = service.retrieveSeatsSchemeModelById(id);
        return mapper.toDto(retrievedSavedSeatsScheme);
    }

    @GetMapping(path = "/get-seats-model/all")
    public List<SeatsSchemeEntityDto> retrieveAllSavedSeatsSchemeModelsFromDb() {
        log.info("Retrieving seat scheme model data for all saved plane models.");
        return seatsSchemeModelServiceImpl.retrieveAllSavedSeatsSchemeModelsFromDb();
    }

    @PutMapping(path = "/update-seats-scheme-model/{id}")
    public SeatsSchemeEntityDto updateSeatsSchemeModel(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        log.info("Updating seat scheme model data with ID: {}.", id);
        return seatsSchemeModelServiceImpl.updateSeatsSchemeModel(id, updateSeatsSchemeCommand);
    }

    @DeleteMapping(path = "/delete-seats-scheme-model/id/{id}")
    public void deleteSeatsSchemeModelById(@PathVariable Long id) {
        seatsSchemeModelServiceImpl.deleteSeatsSchemeModelById(id);
    }

    @DeleteMapping(path = "/delete-seats-scheme-model/plane-name/{planeModelName}")
    public void deleteSeatsSchemeModelByPlaneModelName(@PathVariable String planeModelName) {
        seatsSchemeModelServiceImpl.deleteSeatsSchemeModelByPlaneModelName(planeModelName);
    }

    @DeleteMapping(path = "/delete-all-seats-scheme-models")
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        seatsSchemeModelServiceImpl.deleteAllSavedSeatsSchemeModelsFromDb();
    }
}

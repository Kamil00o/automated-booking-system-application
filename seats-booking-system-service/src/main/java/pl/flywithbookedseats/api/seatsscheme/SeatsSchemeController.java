package pl.flywithbookedseats.api.seatsscheme;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import pl.flywithbookedseats.appservices.SeatsSchemeApplicationService;
import pl.flywithbookedseats.domain.seatsscheme.PageSeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsScheme;
import pl.flywithbookedseats.domain.seatsscheme.SeatsSchemeModelService;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/seats-booking")
public class SeatsSchemeController {

    private final SeatsSchemeModelService seatsSchemeModelServiceImpl;
    private final SeatsSchemeApplicationService service;
    private final CreateSeatsSchemeCommandMapper commandMapper;
    private final SeatsSchemeDtoMapper mapper;
    private final UpdateSeatsSchemeCommandMapper updateMapper;
    private final PageSeatsSchemeDtoMapper pageMapper;

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

    @GetMapping
    public PageSeatsSchemeDto retrieveAllSavedSeatsSchemeModelsFromDb(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "7") int size) {
        log.info("Retrieving seat scheme model data for all saved plane models.");
        Pageable pageable = PageRequest.of(page, size);
        PageSeatsScheme savedPageSeatsScheme = service.retrieveAllSavedSeatsSchemeModelsFromDb(pageable);
        return pageMapper.toDto(savedPageSeatsScheme);
    }

    @PutMapping(path = "/{id}")
    public SeatsSchemeDto updateSeatsSchemeModel(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSeatsSchemeCommand updateSeatsSchemeCommand) {
        log.info("Updating seat scheme model data with ID: {}.", id);
        SeatsScheme updatedSeatsScheme = service
                .updateSeatsSchemeModel(id, updateMapper.toDomain(updateSeatsSchemeCommand));
        return mapper.toDto(updatedSeatsScheme);
    }

    @DeleteMapping(path = "/id/{id}")
    public void deleteSeatsSchemeModelById(@PathVariable Long id) {
        service.deleteSeatsSchemeModelById(id);
    }

    @DeleteMapping(path = "/planeModelName/{planeModelName}")
    public void deleteSeatsSchemeModelByPlaneModelName(@PathVariable String planeModelName) {
        service.deleteSeatsSchemeModelByPlaneModelName(planeModelName);
    }

    @DeleteMapping
    public void deleteAllSavedSeatsSchemeModelsFromDb() {
        service.deleteAllSavedSeatsSchemeModelsFromDb();
    }
}

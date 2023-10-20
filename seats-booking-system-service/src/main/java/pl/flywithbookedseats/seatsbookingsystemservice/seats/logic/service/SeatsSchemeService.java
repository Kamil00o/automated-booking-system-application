package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.command.CreateSeatsScheme;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.domain.SeatsScheme;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.repository.SeatsSchemeRepository;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class SeatsSchemeService {

    private final SeatsSchemeRepository seatsSchemeRepository;

    @Transactional
    public SeatsScheme createSeatsScheme(CreateSeatsScheme createSeatsScheme) {
        if (!exists(createSeatsScheme)) {
            /*SeatsScheme seatsScheme = SeatsScheme.builder()
                    .planeModel(createSeatsScheme.getPlaneModel());*/
        }
        return null;
    }

    private boolean exists(CreateSeatsScheme createSeatsScheme) {
        return seatsSchemeRepository.existsByPlaneModel(createSeatsScheme.planeModel());
    }

    private HashMap<String, Long> attachPlaneSeatsScheme() {
        return null;
    }
}

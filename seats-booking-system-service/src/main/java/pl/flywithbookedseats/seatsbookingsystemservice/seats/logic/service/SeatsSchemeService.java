package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.SeatsSchemeRepository;

@RequiredArgsConstructor
@Service
public class SeatsSchemeService {

    private final SeatsSchemeRepository seatsSchemeRepository;
}

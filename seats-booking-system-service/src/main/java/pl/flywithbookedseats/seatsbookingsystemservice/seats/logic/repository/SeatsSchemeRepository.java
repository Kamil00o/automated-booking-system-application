package pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.seats.logic.model.domain.SeatsScheme;

@Repository
public interface SeatsSchemeRepository extends JpaRepository<SeatsScheme, String> {

    public boolean existsByPlaneModel(String planeModelName);
}

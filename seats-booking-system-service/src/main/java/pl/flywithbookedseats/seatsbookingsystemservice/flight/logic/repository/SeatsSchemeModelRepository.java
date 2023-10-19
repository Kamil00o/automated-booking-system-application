package pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.seatsbookingsystemservice.flight.logic.model.domain.SeatsSchemeModel;

@Repository
public interface SeatsSchemeModelRepository extends JpaRepository<SeatsSchemeModel, Long> {

}

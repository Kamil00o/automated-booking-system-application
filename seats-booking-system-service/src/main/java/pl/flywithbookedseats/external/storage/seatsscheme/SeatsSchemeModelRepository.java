package pl.flywithbookedseats.external.storage.seatsscheme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.flywithbookedseats.external.storage.seatsscheme.SeatsSchemeModel;

import java.util.Optional;

@Repository
public interface SeatsSchemeModelRepository extends JpaRepository<SeatsSchemeModel, Long> {

    boolean existsByPlaneModelName(String PlaneModelName);

    boolean existsById(Long id);

    Optional<SeatsSchemeModel> findByPlaneModelName(String planeModelName);

    Optional<SeatsSchemeModel> findById(Long id);
}

package pl.flywithbookedseats.external.storage.seatsscheme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatsSchemeModelRepository extends JpaRepository<SeatsSchemeEntity, Long> {

    boolean existsByPlaneModelName(String PlaneModelName);

    boolean existsById(Long id);

    Optional<SeatsSchemeEntity> findByPlaneModelName(String planeModelName);

    Optional<SeatsSchemeEntity> findById(Long id);
}

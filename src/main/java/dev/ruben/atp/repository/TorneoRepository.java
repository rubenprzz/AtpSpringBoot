package dev.ruben.atp.repository;

import ch.qos.logback.core.model.INamedModel;
import dev.ruben.atp.models.Modo;
import dev.ruben.atp.models.Torneo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface TorneoRepository extends JpaRepository<Torneo, String> {
    Page<Torneo> findAllByModoContainsIgnoreCase(Modo modo, Pageable pageable);
}

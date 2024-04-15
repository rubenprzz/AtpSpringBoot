package dev.ruben.atp.repository;

import ch.qos.logback.core.model.INamedModel;
import dev.ruben.atp.models.Torneo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TorneoRepository extends JpaRepository<Torneo, UUID> {
}

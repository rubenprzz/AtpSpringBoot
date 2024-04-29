package dev.ruben.atp.repository;

import dev.ruben.atp.models.Participante;
import jakarta.servlet.http.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante,Long> {

    Page<Participante> findAllByTorneoIdSec(Long idSec, Pageable pageable);
    Optional<Participante> findParticipanteByTenistaRanking(Long ranking);


}

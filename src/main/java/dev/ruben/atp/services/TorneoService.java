package dev.ruben.atp.services;

import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.repository.TorneoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class TorneoService extends BaseService<Torneo, String, TorneoRepository> {


    public Optional<Torneo> findTorneoByIdSec(Long idSec) {
        return repositorio.findTorneoByIdSec(idSec);


    }

}

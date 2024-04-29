package dev.ruben.atp.services;

import dev.ruben.atp.models.Participante;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.repository.ParticipanteRepository;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParticipanteService extends BaseService<Participante,Long, ParticipanteRepository>{

    @Autowired
    private ParticipanteService participanteService;

    @Autowired
    private TenistaService tenistaService;

    public Participante saveParticipante(Participante part) {
        Tenista tenista = tenistaService.findById(part.getTenista().getId()).orElseGet(null);
        Participante savedParticipante = repositorio.save(part);
        long puntosAumentar = part.getPuntosOtorgados();
        tenista.setPuntos(tenista.getPuntos() + puntosAumentar);
        tenistaService.save(tenista);
        return savedParticipante;
    }
    public void ordenarRanking() {
        participanteService.findAll().stream().sorted((p1, p2) -> {
            if (p1.getPuntosOtorgados() > p2.getPuntosOtorgados()) {
                return 1;
            } else if (p1.getPuntosOtorgados() < p2.getPuntosOtorgados()) {
                return -1;
            } else {
                return 0;
            }
        });
    }
    public Page<Participante> findAllByTorneoIdSec(Long idSec, Pageable pageable) {
        return repositorio.findAllByTorneoIdSec(idSec, pageable);
    }
    public Optional<Participante> findParticipanteByTenistaRanking(Long ranking) {
        return repositorio.findParticipanteByTenistaRanking(ranking);
    }





}


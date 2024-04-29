
package dev.ruben.atp.controllers;

import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.exceptions.CategoriaBadRequestException;
import dev.ruben.atp.mapper.TorneoMapper;
import dev.ruben.atp.models.Participante;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.repository.ParticipanteRepository;
import dev.ruben.atp.services.ParticipanteService;
import dev.ruben.atp.services.TenistaService;
import dev.ruben.atp.services.TorneoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService participacionService;
    private final TenistaService tenistaService;
    private final TorneoService torneoService;
    private final TorneoMapper torneoMapper;

    @GetMapping
    public ResponseEntity<?> getParticipantes() {
        return ResponseEntity.ok(participacionService.findAll());
    }
    @GetMapping("/{id}")

    public ResponseEntity<?> getParticipante(@PathVariable Long id) {
        return ResponseEntity.ok(participacionService.findById(id));
    }
    @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')"+" || hasRole('ADMIN_TENISTA')")
    public ResponseEntity<?> deleteParticipante(@PathVariable Long id) {
        participacionService.findById(id)
                .orElseThrow(() -> new RuntimeException(HttpStatus.NOT_FOUND +"No se ha encontrado un particimante con el id: " + id));
        participacionService.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')"+" || hasRole('ADMIN_TENISTA')")
    public ResponseEntity<?> createParticipante(@RequestBody Participante participante) {
        Tenista tenista = tenistaService.findById(participante.getTenista().getId())
                .orElseThrow(() -> new RuntimeException("Tenista not found with id " + participante.getTenista().getId()));

        Torneo torneo = torneoService.findById(participante.getTorneo().getId())
                .orElseThrow(() -> new RuntimeException("Torneo not found with id " + participante.getTorneo().getId()));
        var categoria=torneo.getCategoria();
        var modo=torneo.getModo().toString();
        var modoTenista=tenista.getModo().toString();
        if(!modo.equals(modoTenista)){
            throw new CategoriaBadRequestException("El modo del torneo no coincide con el modo del tenista");

        }
        participante.setPuntosBasedOnResult(categoria.toString());

        participante.setTenista(tenista);
        participante.setResultado(participante.getResultado());
        participante.getTenista().setPuntos(participante.getTenista().getPuntos()+participante.getPuntosOtorgados());
        participante.setDineroGanadoBasedOnResult(categoria.toString());
        participante.setTorneo(torneo);
        participacionService.save(participante);
        participacionService.ordenarRanking();

        return ResponseEntity.ok().body(participante);
    }
    @GetMapping("/torneo/{idSec}")
    public Page<Participante> findAllByTorneo(@PathVariable Long idSec, Pageable pageable) {
        return participacionService.findAllByTorneoIdSec(idSec, pageable);

    }
    @GetMapping("/ranking/{ranking}")
    public ResponseEntity<?> findParticipanteByTenistaRanking(@PathVariable Long ranking) {
        return ResponseEntity.ok(participacionService.findParticipanteByTenistaRanking(ranking));
    }




}


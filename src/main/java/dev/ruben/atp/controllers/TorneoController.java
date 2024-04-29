package dev.ruben.atp.controllers;

import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.dto.TorneoUpdateDto;
import dev.ruben.atp.exceptions.TorneoNotFoundException;
import dev.ruben.atp.mapper.TorneoMapper;
import dev.ruben.atp.models.Categoria;
import dev.ruben.atp.models.Modo;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.services.TorneoService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/torneos")
public class TorneoController {
    private final TorneoService torneoService;
    private final PaginationLinksUtils paginationLinksUtils;
    private final TorneoMapper torneoMapper;

    @GetMapping
    public ResponseEntity<Page<TorneoResponseDTO>> getTorneos(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
        Page<Torneo> torneos = torneoService.findAll(pageable);
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(torneos, uriComponentsBuilder))
                .body(torneos.map(torneoMapper::toTorneoResponseDTO));

    }

    @GetMapping("/{id}")

    public ResponseEntity<Optional<TorneoResponseDTO>> getTorneoById(@PathVariable String id) {

        var torneo = torneoService.findById(id);
        if (torneo.isEmpty()) {
            throw new TorneoNotFoundException("Torneo no encontrado " + id);

        }
        return ResponseEntity.ok(torneo.map(torneoMapper::toTorneoResponseDTO));
    }

    @GetMapping("/id/{idSec}")
    public ResponseEntity<Optional<TorneoResponseDTO>> getTorneoByIdSec(@PathVariable Long idSec) {

        var torneo = torneoService.findTorneoByIdSec(idSec);
        if (torneo.isEmpty()) {
            throw new TorneoNotFoundException("Torneo no encontrado " + idSec);

        }
        return ResponseEntity.ok(torneo.map(torneoMapper::toTorneoResponseDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')"+" || hasRole('ADMIN_TORNEO')")
    public ResponseEntity<Void> deleteTorneoById(@PathVariable String id) {
        var torneo = torneoService.findById(id);
        if (torneo.isPresent()) {
            torneoService.deleteById(id);
            log.info("Borrando torneo con id" + id);
            return ResponseEntity.noContent().build();


        }
        throw new TorneoNotFoundException("Torneo con id " + id + " no encontrado");


    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')"+" || hasRole('ADMIN_TORNEO')")

    public ResponseEntity<TorneoResponseDTO> updateTorneo(@PathVariable String id, @RequestBody TorneoUpdateDto torneoUpdateDto) {
        Torneo torneoActual = torneoService.findById(id).orElseThrow(() -> new TorneoNotFoundException("Torneo no encontrado " + id));
        if (torneoActual == null) {
            throw new TorneoNotFoundException("Torneo no encontrado " + id);
        }
        torneoActual.setCategoria(torneoUpdateDto.getCategoria() != null ? Categoria.valueOf(torneoUpdateDto.getCategoria()) : torneoActual.getCategoria());
        torneoActual.setEntradas(torneoUpdateDto.getEntradas() != null ? torneoUpdateDto.getEntradas() : torneoActual.getEntradas());
        torneoActual.setFechaInicio(torneoUpdateDto.getFechaInicio() != null ? torneoUpdateDto.getFechaInicio() : torneoActual.getFechaInicio());
        torneoActual.setFechaFin(torneoUpdateDto.getFechaFin() != null ? torneoUpdateDto.getFechaFin() : torneoActual.getFechaFin());
        torneoActual.setIdSec(torneoUpdateDto.getIdSec() != null ? torneoUpdateDto.getIdSec() : torneoActual.getIdSec());
        torneoActual.setPremio(torneoUpdateDto.getPremio() != null ? torneoUpdateDto.getPremio() : torneoActual.getPremio());
        torneoActual.setUbicacion(torneoUpdateDto.getUbicacion() != null ? torneoUpdateDto.getUbicacion() : torneoActual.getUbicacion());
        torneoActual.setModo(torneoUpdateDto.getModo() != null ? Modo.valueOf(torneoUpdateDto.getModo()) : torneoActual.getModo());
        return ResponseEntity.ok(torneoMapper.toTorneoResponseDTO(torneoService.save(torneoActual)));


    }
    @PutMapping("/id/{idSec}")
    @PreAuthorize("hasRole('ADMIN')"+" || hasRole('ADMIN_TORNEO')")

    public ResponseEntity<TorneoResponseDTO> updateTorneo(@PathVariable Long idSec, @RequestBody TorneoUpdateDto torneoUpdateDto) {
        Torneo torneoActual = torneoService.findTorneoByIdSec(idSec).orElseThrow(() -> new TorneoNotFoundException("Torneo no encontrado " + idSec));
        if (torneoActual == null) {
            throw new TorneoNotFoundException("Torneo no encontrado " + idSec);
        }
        Torneo torneoUpdated = torneoService.save(torneoMapper.toTorneo(torneoUpdateDto, torneoActual));
        torneoActual.setCategoria(torneoUpdateDto.getCategoria() != null ? Categoria.valueOf(torneoUpdateDto.getCategoria()) : torneoActual.getCategoria());
        torneoActual.setEntradas(torneoUpdateDto.getEntradas() != null ? torneoUpdateDto.getEntradas() : torneoActual.getEntradas());
        torneoActual.setFechaInicio(torneoUpdateDto.getFechaInicio() != null ? torneoUpdateDto.getFechaInicio() : torneoActual.getFechaInicio());
        torneoActual.setFechaFin(torneoUpdateDto.getFechaFin() != null ? torneoUpdateDto.getFechaFin() : torneoActual.getFechaFin());
        torneoActual.setIdSec(torneoUpdateDto.getIdSec() != null ? torneoUpdateDto.getIdSec() : torneoActual.getIdSec());
        torneoActual.setPremio(torneoUpdateDto.getPremio() != null ? torneoUpdateDto.getPremio() : torneoActual.getPremio());
        torneoActual.setUbicacion(torneoUpdateDto.getUbicacion() != null ? torneoUpdateDto.getUbicacion() : torneoActual.getUbicacion());
        torneoActual.setModo(torneoUpdateDto.getModo() != null ? Modo.valueOf(torneoUpdateDto.getModo()) : torneoActual.getModo());
        return ResponseEntity.ok(torneoMapper.toTorneoResponseDTO(torneoService.save(torneoActual)));


    }
}


package dev.ruben.atp.controllers;

import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.exceptions.StorageBadRequest;
import dev.ruben.atp.exceptions.StorageInternal;
import dev.ruben.atp.exceptions.TenistaNotFoundException;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.services.TenistaService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenistas")
public class TenistaController {

    private final TenistaService tenistaService;
    private final PaginationLinksUtils paginationLinksUtils;
    private final TenistaMapper tenistaMapper;

    @GetMapping

    public ResponseEntity<?> getTenistas(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request,
                                         @RequestParam(required = false) Optional<String> nombreCompleto,
                                         @RequestParam(required = false) Optional<String> pais,
                                         @RequestParam(required = false) Optional<Double> altura,
                                         @RequestParam(required = false) Optional<Double> peso
                                         ) {
        log.info("getTenistas");
        Page<TenistaResponseDTO> tenistas = tenistaService.findAll(nombreCompleto, pais, altura, peso, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        log.info("fin tenistas");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(tenistas, uriBuilder))
                .body(tenistas);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTenistaById(@PathVariable Long id) {
        log.info("getTenistaById");
        Optional<Tenista> tenista = tenistaService.findById(id);
        if (tenista.isEmpty()) {
            throw new TenistaNotFoundException("Tenista not found");
        } else
            return ResponseEntity.ok().body(tenista.get());


    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    public ResponseEntity<?> saveTenista(@RequestBody @Valid TenistaCreateDTO tenista) {
        log.info("saveTenista");
        Tenista tenistaSaved = tenistaService.save(tenistaMapper.convertoToTenista(tenista));
        log.info("fin saveTenista");
        return ResponseEntity.ok(tenistaSaved);
    }
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    @PutMapping("/{id}")

    public ResponseEntity<TenistaResponseDTO
            > updateTenista(@PathVariable Long id, @Valid @RequestBody TenistaUpdateDTO tenistaUpdateDTO) {
        log.info("updateTenista");
        Tenista existingTenista = tenistaService.findById(id).orElseThrow(() -> new TenistaNotFoundException("Tenista not found"));
        if (existingTenista == null) {
            throw new TenistaNotFoundException("Tenista not found");
        } else {
            existingTenista.setNombreCompleto(tenistaUpdateDTO.getNombreCompleto() != null ? tenistaUpdateDTO.getNombreCompleto() : existingTenista.getNombreCompleto());
            existingTenista.setPais(tenistaUpdateDTO.getPais() != null ? tenistaUpdateDTO.getPais() : existingTenista.getPais());
            existingTenista.setFechaNacimiento(tenistaUpdateDTO.getFechaNacimiento() != null ? LocalDate.from(tenistaUpdateDTO.getFechaNacimiento()) : existingTenista.getFechaNacimiento());
            existingTenista.setAltura(tenistaUpdateDTO.getAltura() != null ? tenistaUpdateDTO.getAltura() : existingTenista.getAltura());
            existingTenista.setPeso(tenistaUpdateDTO.getPeso() != null ? tenistaUpdateDTO.getPeso() : existingTenista.getPeso());
            existingTenista.setDineroGanado(tenistaUpdateDTO.getDineroGanado() != null ? tenistaUpdateDTO.getDineroGanado() : existingTenista.getDineroGanado());
            existingTenista.setEntrenador(tenistaUpdateDTO.getEntrenador() != null ? tenistaUpdateDTO.getEntrenador() : existingTenista.getEntrenador());
            existingTenista.setRanking(tenistaUpdateDTO.getRanking() != null ? tenistaUpdateDTO.getRanking() : existingTenista.getRanking());
            existingTenista.setPuntos(tenistaUpdateDTO.getPuntos() != null ? tenistaUpdateDTO.getPuntos() : existingTenista.getPuntos());
            existingTenista.setImagen(tenistaUpdateDTO.getImagen() != null ? tenistaUpdateDTO.getImagen() : existingTenista.getImagen());
            existingTenista.setUpdated(LocalDateTime.now());
            return ResponseEntity.ok(tenistaMapper.toTenistaResponseDTO(tenistaService.save(existingTenista)));

        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    public ResponseEntity<TenistaResponseDTO> deleteTenistaById(@PathVariable @RequestParam Long id) {
        log.info("deleteTenistaById{}", id);
        var tenista = tenistaService.findById(id);
        if (tenista.isPresent()) {
            tenistaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new TenistaNotFoundException("No se ha podido encontrar al tenista con id" + id);


    }



    @PatchMapping(value = "/imagen/{id}", consumes = {"*/*"})
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    public ResponseEntity<TenistaResponseDTO> updateImage(@PathVariable Long id, @RequestPart("file") MultipartFile imagen) {
        log.info("Actualizando imagen del tenista con id: " + id);
        List<String> datosPermitidos = List.of("image/png", "image/jpg", "image/jpeg", "image/gif");

        try {
            String contentType = imagen.getContentType();
            log.info("Tipo de contenido de la imagen: " + contentType);

            if (!imagen.isEmpty() && contentType != null && !contentType.isEmpty() && datosPermitidos.contains(contentType.toLowerCase())) {
                log.info("Tipo de imagen válido. Procediendo con la actualización...");
                return ResponseEntity.ok(tenistaService.updateImage(id, imagen, true));
            } else {
                log.warn("Tipo de imagen no válido o imagen vacía. Lanzando excepción...");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se ha enviado una imagen válida para el cliente");
            }
        } catch (Exception e) {
            log.error("Error al procesar la imagen: " + e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede determinar el tipo de la imagen");
        }
    }


}

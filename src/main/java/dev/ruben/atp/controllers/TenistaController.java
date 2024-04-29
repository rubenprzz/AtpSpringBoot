package dev.ruben.atp.controllers;

import dev.ruben.atp.dto.TenistaCreateDTO;
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

    public ResponseEntity<?> getTenistas(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
        log.info("getTenistas");
        Page<Tenista> tenistas = tenistaService.findAll(pageable);
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

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    public ResponseEntity<?> updateTenista(@PathVariable Long id, @Valid @RequestBody TenistaUpdateDTO tenista) {
        log.info("updateTenista");
        Tenista existingTenista = tenistaService.findById(id).orElseThrow(() -> new TenistaNotFoundException("Tenista not found"));
        if (existingTenista== null) {
            throw new TenistaNotFoundException("Tenista not found");
        } else {
        Tenista tenistaUpdated = tenistaService.save(tenistaMapper.convertoToTenista(tenista, existingTenista));
            existingTenista.setNombreCompleto(tenista.getNombreCompleto() != null ? tenista.getNombreCompleto() : existingTenista.getNombreCompleto());
            existingTenista.setPais(tenista.getPais() != null ? tenista.getPais() : existingTenista.getPais());
            existingTenista.setFechaNacimiento(tenista.getFechaNacimiento() != null ? LocalDate.from(tenista.getFechaNacimiento()) : existingTenista.getFechaNacimiento());
            existingTenista.setAltura(tenista.getAltura() != null ? tenista.getAltura() : existingTenista.getAltura());
            existingTenista.setPeso(tenista.getPeso() != null ? tenista.getPeso() : existingTenista.getPeso());
            existingTenista.setDineroGanado(tenista.getDineroGanado() != null ? tenista.getDineroGanado() : existingTenista.getDineroGanado());
            existingTenista.setEntrenador(tenista.getEntrenador() != null ? tenista.getEntrenador() : existingTenista.getEntrenador());
            existingTenista.setRanking(tenista.getRanking() != null ? tenista.getRanking() : existingTenista.getRanking());
            existingTenista.setPuntos(tenista.getPuntos() != null ? tenista.getPuntos() : existingTenista.getPuntos());
            existingTenista.setUpdated(LocalDateTime.now());
            return ResponseEntity.ok(tenistaService.save(existingTenista));

        }
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN_TENISTA')" + "|| hasRole('ADMIN')")
    public ResponseEntity<?> deleteTenistaById(@RequestParam Long id) {
        log.info("deleteTenistaById{}", id);
        var tenista = tenistaService.findById(id);
        if (tenista.isPresent()) {
            tenistaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        throw new TenistaNotFoundException("No se ha podido encontrar al tenista con id" + id);


    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<Tenista> nuevoProducto(@RequestPart("nuevo") TenistaCreateDTO nuevo,
                                                 @RequestPart("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(tenistaService.nuevoTenista(nuevo, file));
    }

    @PatchMapping(value = "/imagen/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Tenista> updateImage(@PathVariable Long id, @RequestPart("imagen") MultipartFile imagen) {
        log.info("Iniciando actualización de imagen mediante el endpoint PATCH para el tenista con el número: " + id);

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

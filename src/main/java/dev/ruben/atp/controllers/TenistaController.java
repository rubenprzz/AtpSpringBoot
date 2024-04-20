package dev.ruben.atp.controllers;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tenistas")
public class TenistaController {

    private final TenistaService tenistaService;
    private final PaginationLinksUtils paginationLinksUtils;

    @GetMapping
    public ResponseEntity<?> getTenistas(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
        log.info("getTenistas");
        Page<Tenista> tenistas = tenistaService.findAll(pageable);
        log.info("fin tenistas");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());

        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(tenistas, uriBuilder))
                .body(tenistas.getContent());

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getTenistaById(@PathVariable Long id) {
        log.info("getTenistaById");
        Optional<Tenista> tenista = tenistaService.findById(id);
        log.info("fin tenista");
        return ResponseEntity.ok(tenista);
    }

    @PostMapping
    public ResponseEntity<?> saveTenista(@RequestBody @Valid Tenista tenista) {
        log.info("saveTenista");
        Tenista tenistaSaved = tenistaService.save(tenista);
        log.info("fin saveTenista");
        return ResponseEntity.ok(tenistaSaved);
    }




}

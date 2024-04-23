package dev.ruben.atp.controllers;

import dev.ruben.atp.exceptions.TorneoNotFoundException;
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
    @GetMapping
    public ResponseEntity<Page<Torneo>> getTorneos(@PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
                Page<Torneo> torneos=torneoService.findAll(pageable);
        UriComponentsBuilder uriComponentsBuilder= UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link", paginationLinksUtils.createLinkHeader(torneos, uriComponentsBuilder))
                .body(torneos);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Torneo>> getTorneoById(@PathVariable String id){

        var torneo=torneoService.findById(id);
        if(torneo.isEmpty()){

        }
        return ResponseEntity.ok(torneo);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTorneoById(@PathVariable String id){
        var torneo=torneoService.findById(id);
        if(torneo==null){
            throw new TorneoNotFoundException("Torneo con id " +id+" no encontrado");


        }
        torneoService.deleteById(id);
        log.info("Borrando torneo con id"+id);
        return ResponseEntity.noContent().build();
    }

    }


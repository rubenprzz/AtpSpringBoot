package dev.ruben.atp.controllers;

import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.services.TenistaService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
public class TenistaController {

    private final TenistaService tenistaService;
    private final PaginationLinksUtils paginationLinksUtils;

    @GetMapping("/tenistas")//SIN PAGEABLE
    public ResponseEntity<List<Tenista>> getTenistas(HttpServletRequest request){
        List<Tenista> tenistas = tenistaService.findAll();
        log.info("Fetching all tenistas");
        log.info("Tenistas: {}", tenistas);
        if(tenistas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tenistas);
    }
}

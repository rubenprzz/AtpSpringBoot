package dev.ruben.atp.services;

import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.exceptions.TenistaNotFoundException;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class TenistaServiceImpl extends BaseService<Tenista, Long, TenistaRepository> implements TenistaService {
    private final TenistaRepository repositorio;
    private final TenistaMapper tenistaMapper;

    @Override
    public Page<TenistaResponseDTO> findAll(Optional<String> nombreCompleto, Optional<String> pais, Optional<Double> altura, Optional<Double> peso, Pageable pageable) {
        Specification<Tenista> specNombreCompletoTenista = (root, query, criteriaBuilder) ->
                nombreCompleto.map(m -> criteriaBuilder.like(criteriaBuilder.lower(root.get("nombreCompleto")), "%" + m.toLowerCase() + "%")) // Buscamos por marca
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        Specification<Tenista> specPaisTenista = (root, query, criteriaBuilder) ->
                pais.map(m -> criteriaBuilder.like(criteriaBuilder.lower(root.get("pais")), "%" + m.toLowerCase() + "%"))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        Specification<Tenista> specAlturaTenista = (root, query, criteriaBuilder) ->
                altura.map(m -> criteriaBuilder.equal(root.get("altura"), m))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        Specification<Tenista> specPesoTenista = (root, query, criteriaBuilder) ->
                peso.map(m -> criteriaBuilder.equal(root.get("peso"), m))
                        .orElseGet(() -> criteriaBuilder.isTrue(criteriaBuilder.literal(true)));
        Specification<Tenista> tenista= Specification.where(specNombreCompletoTenista).and(specPaisTenista).and(specAlturaTenista).and(specPesoTenista);
        return repositorio.findAll(tenista, pageable).map(tenistaMapper::toTenistaResponseDTO);





    }








}

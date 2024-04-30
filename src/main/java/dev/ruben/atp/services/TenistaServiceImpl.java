package dev.ruben.atp.services;

import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.exceptions.TenistaNotFoundException;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class TenistaServiceImpl extends BaseService<Tenista, Long, TenistaRepository> implements TenistaService {
    private final TenistaRepository repositorio;
    private final StorageService storageService;
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
    @Override
    @Transactional
    public TenistaResponseDTO updateImage(Long id, MultipartFile image, Boolean withUrl) {
        log.info("Actualizando imagen de producto por id: " + id);
        var tenistaActual = repositorio.findById(id).orElseThrow(() -> new TenistaNotFoundException("Tenista no encontrado con id: " + id));
        if (tenistaActual.getImagen() != null && !tenistaActual.getImagen().equals(Tenista.IMAGE_DEFAULT)) {
            storageService.delete(tenistaActual.getImagen());
        }
        String imageStored = storageService.store(image);
        String imageUrl = !withUrl ? imageStored : storageService.getUrl(imageStored);
        storageService.getUrl(imageStored);
        var tenistaActualizado = Tenista.builder()
                .id(tenistaActual.getId())
                .nombreCompleto(tenistaActual.getNombreCompleto())
                .pais(tenistaActual.getPais())
                .altura(tenistaActual.getAltura())
                .peso(tenistaActual.getPeso())
                .fechaNacimiento(tenistaActual.getFechaNacimiento())
                .edad(tenistaActual.getEdad())
                .fechaProfesional(tenistaActual.getFechaProfesional())
                .mano(tenistaActual.getMano())
                .reves(tenistaActual.getReves())
                .entrenador(tenistaActual.getEntrenador())
                .dineroGanado(tenistaActual.getDineroGanado())
                .bestRanking(tenistaActual.getBestRanking())
                .modo(tenistaActual.getModo())
                .wins(tenistaActual.getWins())
                .loses(tenistaActual.getLoses())
                .winrate(tenistaActual.getWinrate())
                .imagen(imageUrl)

                .build();

        var tenistaUpdated = repositorio.save(tenistaActualizado);

        return tenistaMapper.toTenistaResponseDTO(tenistaUpdated);
    }




}

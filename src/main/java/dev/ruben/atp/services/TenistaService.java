package dev.ruben.atp.services;

import dev.ruben.atp.controllers.StorageController;
import dev.ruben.atp.controllers.StorageController;
import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import dev.ruben.atp.services.BaseService;
import dev.ruben.atp.services.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.awt.print.Pageable;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class TenistaService extends BaseService<Tenista, Long, TenistaRepository> {
    private final StorageService storageService;



    public Tenista nuevoTenista(TenistaCreateDTO nuevo, MultipartFile file) {
        String urlImagen = null;

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            urlImagen = MvcUriComponentsBuilder
                    .fromMethodName(StorageController.class, "serveFile", imagen, null)
                    .build().toUriString();
        }

        nuevo.setImagen(urlImagen);

        Tenista nuevoTenista = Tenista.builder()
                .nombreCompleto(nuevo.getNombreCompleto())
                .fechaNacimiento(nuevo.getFechaNacimiento())
                .altura(nuevo.getAltura())
                .bestRanking(nuevo.getRanking())
                .fechaProfesional(nuevo.getFechaProfesional())
                .dineroGanado(nuevo.getDineroGanado())
                .loses(Double.valueOf(nuevo.getLoses()))
                .peso(nuevo.getPeso())
                .wins(Double.valueOf(nuevo.getWins()))
                .mano(nuevo.getMano())
                .reves(nuevo.getReves())
                .puntos(nuevo.getPuntos())
                .imagen(nuevo.getImagen())
                .build();

        return this.save(nuevoTenista);
    }
    @Transactional
    public Tenista updateImage(Long id, MultipartFile image, Boolean withUrl) {
        log.info("Iniciando actualización de imagen para el tenista con el número: " + id);
        var tenista = findById(id).orElseThrow();
        log.info("Tenista encontrado: " + tenista);

        if (tenista.getImagen() != null) {
            log.info("Eliminando imagen anterior del tenista: " + tenista.getImagen());
            storageService.delete(tenista.getImagen());
        }

        log.info("Almacenando nueva imagen...");
        var imagen = storageService.store(image);

        String imageUrl = !withUrl ? imagen : storageService.getUrl(imagen);
        log.info("URL de la nueva imagen: " + imageUrl);

        tenista.setImagen(imageUrl);
        Tenista updatedTenista = this.save(tenista);
        log.info("Imagen actualizada correctamente para el tenista con el número: " + id);

        return updatedTenista;
    }

}

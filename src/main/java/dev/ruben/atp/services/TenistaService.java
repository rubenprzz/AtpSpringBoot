package dev.ruben.atp.services;

import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.models.Tenista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface TenistaService {

    Page<TenistaResponseDTO> findAll(Optional<String> nombreCompleto, Optional<String> pais, Optional<Double> altura, Optional<Double> peso, Pageable pageable);
    Optional<Tenista> findById(Long id);
    Tenista save(Tenista tenista);
    void deleteById(Long id);
    TenistaResponseDTO updateImage(Long id, MultipartFile image, Boolean withUrl);

}

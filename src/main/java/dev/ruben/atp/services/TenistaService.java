package dev.ruben.atp.services;

import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class TenistaService extends BaseService<Tenista, Long, TenistaRepository>{
    public Page<Tenista> findTenistaByNombreCompletoIgnoreCase(Optional<String> nombreCompleto, Pageable pageable){
        return repositorio.findTenistaByNombreCompletoIgnoreCase(String.valueOf(nombreCompleto), pageable);
    }

}

package dev.ruben.atp.mapper;

import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Mano;
import dev.ruben.atp.models.Modo;
import dev.ruben.atp.models.Reves;
import dev.ruben.atp.models.Tenista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TenistaMapperTest {

    private TenistaMapper tenistaMapper;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        tenistaMapper = new TenistaMapper(modelMapper);
    }

    @Test
    @DisplayName("Should map Tenista to TenistaCreateDTO")
    public void shouldMapTenistaToTenistaCreateDTO() {
        Tenista tenista = new Tenista();

        TenistaCreateDTO result = tenistaMapper.convertoToTenistaDto(tenista);

        // Assert fields
    }

    @Test
    @DisplayName("Should map TenistaCreateDTO to Tenista")
    public void shouldMapTenistaCreateDTOToTenista() {
        TenistaCreateDTO tenistaCreateDTO = new TenistaCreateDTO();
        // Set fields of tenistaCreateDTO
        Tenista result = tenistaMapper.convertoToTenista(tenistaCreateDTO);

        // Assert fields
    }

  /*  @Test
    @DisplayName("Should map Tenista to TenistaUpdateDTO")
    public void shouldMapTenistaToTenistaUpdateDTO() {
        Tenista tenista = new Tenista(1L);
        tenista.setRanking(1L);
        tenista.setNombreCompleto("nombre");
        tenista.setPais("suiza");
        tenista.setFechaNacimiento(LocalDate.now());
        tenista.setAltura(1.89);
        tenista.setPeso(78.7);
        tenista.setFechaProfesional(LocalDate.now());
        tenista.setMano(Mano.DIESTRO);
        tenista.setReves(Reves.DOS_MANOS);
        tenista.setEntrenador("pepe");
        tenista.setDineroGanado(100.0);
        tenista.setBestRanking(10L);
        tenista.setModo(Modo.INDIVIDUAL);
        tenista.setWins(10.0);
        tenista.setLoses(10.0);
        tenista.setImagen("IMAGEN");

        TenistaUpdateDTO result = tenistaMapper.convertoToTenistaUpdateDTO(tenista);

    }*/

    @Test
    @DisplayName("Should map TenistaUpdateDTO to Tenista")
    public void shouldMapTenistaUpdateDTOToTenista() {
        TenistaUpdateDTO tenistaUpdateDTO = new TenistaUpdateDTO("nombre","suiza","pepe",100.0,10L,10L,100L,10L,1.89,78.7, Mano.DIESTRO.toString(), Reves.DOS_MANOS.toString(), LocalDate.now(),LocalDate.now(),1L,1L,"IMAGEN");
        Tenista existingTenista = new Tenista();

        Tenista result = tenistaMapper.convertoToTenista(tenistaUpdateDTO, existingTenista);

    }

    @Test
    @DisplayName("Should map Tenista to TenistaResponseDTO")
    public void shouldMapTenistaToTenistaResponseDTO() {
        Tenista tenista = new Tenista();
        // Set fields of tenista

        TenistaResponseDTO result = tenistaMapper.toTenistaResponseDTO(tenista);

        // Assert fields
    }
}
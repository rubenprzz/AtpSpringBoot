package dev.ruben.atp.mapper;

import dev.ruben.atp.dto.TorneoCreateDto;
import dev.ruben.atp.dto.TorneoResponseDTO;
import dev.ruben.atp.mapper.TorneoMapper;
import dev.ruben.atp.models.Categoria;
import dev.ruben.atp.models.Modo;
import dev.ruben.atp.models.Torneo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TorneoMapperTest {

    private TorneoMapper torneoMapper;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        modelMapper = new ModelMapper();
        torneoMapper = new TorneoMapper(modelMapper);
    }

    @Test
    public void shouldMapTorneoResponseDtoToTorneo() {
        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();
        torneoResponseDTO.setCategoria(String.valueOf(Categoria.MASTER_1000));

        Torneo torneo = torneoMapper.toTorneo(torneoResponseDTO);

        assertNotNull(torneo);
        assertEquals(torneoResponseDTO.getCategoria().toString(), torneo.getCategoria().toString());

    }

    @Test
    public void shouldReturnNullWhenTorneoResponseDtoIsNull() {
        TorneoResponseDTO torneoResponseDTO = TorneoResponseDTO.builder().build();
        Torneo torneo = torneoMapper.toTorneo(torneoResponseDTO);
        assertAll(

                () -> assertNull(torneoResponseDTO.getCategoria()),
                () -> assertNull(torneoResponseDTO.getFechaInicio()),
                () -> assertNull(torneoResponseDTO.getFechaFin()),
                () -> assertNull(torneoResponseDTO.getIdSec()),
                () -> assertNull(torneoResponseDTO.getModo()),
                () -> assertNull(torneoResponseDTO.getPremio()),
                () -> assertNull(torneoResponseDTO.getUbicacion()),
                () -> assertNull(torneoResponseDTO.getParticipantes())

                );



    }
    @Test
    public void shouldMapTorneoResponseDtoToTorneoWithNullValues() {
        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();
        torneoResponseDTO.setCategoria(null);

        Torneo torneo = torneoMapper.toTorneo(torneoResponseDTO);

        assertNotNull(torneo);
        assertNull(torneo.getCategoria());

    }
    @Test
    public void shouldMapTorneoResponseDtoToTorneoWithAllValues() {
        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();
        torneoResponseDTO.setCategoria(String.valueOf(Categoria.MASTER_1000));
        torneoResponseDTO.setFechaInicio("2021-10-10");
        torneoResponseDTO.setFechaFin("2021-10-20");
        torneoResponseDTO.setIdSec(1L);
        torneoResponseDTO.setModo(Modo.INDIVIDUAL.toString());
        torneoResponseDTO.setPremio(100L);
        torneoResponseDTO.setUbicacion("Madrid");
        torneoResponseDTO.setParticipantes(null);

        Torneo torneo = torneoMapper.toTorneo(torneoResponseDTO);

        assertNotNull(torneo);
        assertEquals(torneoResponseDTO.getCategoria().toString(), torneo.getCategoria().toString());
        assertEquals(torneoResponseDTO.getFechaInicio(), torneo.getFechaInicio());
        assertEquals(torneoResponseDTO.getFechaFin(), torneo.getFechaFin());
        assertEquals(torneoResponseDTO.getIdSec(), torneo.getIdSec());
        assertEquals(torneoResponseDTO.getModo().toString(), torneo.getModo().toString());
        assertEquals(torneoResponseDTO.getPremio(), torneo.getPremio());
        assertEquals(torneoResponseDTO.getUbicacion(), torneo.getUbicacion());
        assertEquals(torneoResponseDTO.getParticipantes(), torneo.getParticipantes());

    }
    @Test
    public void shouldMapTorneoToTorneoResponseDto() {
        Torneo torneo = new Torneo();
        torneo.setCategoria(Categoria.MASTER_1000);
        torneo.setFechaInicio("2021-10-10");
        torneo.setFechaFin("2021-10-20");
        torneo.setIdSec(1L);
        torneo.setModo(Modo.INDIVIDUAL);
        torneo.setPremio(100L);
        torneo.setUbicacion("Madrid");
        torneo.setParticipantes(null);

        TorneoResponseDTO torneoResponseDTO = torneoMapper.toTorneoResponseDTO(torneo);

        assertNotNull(torneoResponseDTO);
        assertEquals(torneo.getCategoria().toString(), torneoResponseDTO.getCategoria().toString());
        assertEquals(torneo.getFechaInicio(), torneoResponseDTO.getFechaInicio());
        assertEquals(torneo.getFechaFin(), torneoResponseDTO.getFechaFin());
        assertEquals(torneo.getIdSec(), torneoResponseDTO.getIdSec());
        assertEquals(torneo.getModo().toString(), torneoResponseDTO.getModo().toString());
        assertEquals(torneo.getPremio(), torneoResponseDTO.getPremio());
        assertEquals(torneo.getUbicacion(), torneoResponseDTO.getUbicacion());
        assertEquals(torneo.getParticipantes(), torneoResponseDTO.getParticipantes());

    }
    @Test
    public void shouldMapTorneoUpdateDtoToTorneo() {
        var fechaInicio = LocalDateTime.now();
        Torneo torneo = new Torneo();
        torneo.setCategoria(Categoria.MASTER_1000);
        torneo.setFechaInicio("2021-10-10");
        torneo.setFechaFin("2021-10-20");
        torneo.setIdSec(1L);
        torneo.setModo(Modo.INDIVIDUAL);
        torneo.setPremio(100L);
        torneo.setUbicacion("Madrid");
        torneo.setParticipantes(null);

        TorneoCreateDto torneoCreateDto = new TorneoCreateDto();
        torneoCreateDto.setCategoria(Categoria.MASTER_1000);
        torneoCreateDto.setFechaInicio("2021-10-10");
        torneoCreateDto.setFechaFin("2021-10-20");
        torneoCreateDto.setIdSec(1L);
        torneoCreateDto.setModo(Modo.INDIVIDUAL.toString());
        torneoCreateDto.setPremio(100L);
        torneoCreateDto.setUbicacion("Madrid");

        Torneo torneo1 = torneoMapper.toTorneo(torneoCreateDto);

        assertNotNull(torneo1);
        assertEquals(torneo.getCategoria().toString(), torneo1.getCategoria().toString());
        assertEquals(torneo.getFechaInicio(), torneo1.getFechaInicio());
        assertEquals(torneo.getFechaFin(), torneo1.getFechaFin());
        assertEquals(torneo.getIdSec(), torneo1.getIdSec());
        assertEquals(torneo.getModo().toString(), torneo1.getModo().toString());
        assertEquals(torneo.getPremio(), torneo1.getPremio());
        assertEquals(torneo.getUbicacion(), torneo1.getUbicacion());

    }
    @Test
    public void shouldMapTorneoUpdateDtoToTorneoWithNullValues() {
        TorneoCreateDto torneoCreateDto = new TorneoCreateDto();
        torneoCreateDto.setCategoria(null);
        torneoCreateDto.setFechaInicio(null);
        torneoCreateDto.setFechaFin(null);
        torneoCreateDto.setIdSec(null);
        torneoCreateDto.setModo(null);
        torneoCreateDto.setPremio(null);
        torneoCreateDto.setUbicacion(null);

        Torneo torneo = torneoMapper.toTorneo(torneoCreateDto);

        assertNotNull(torneo);
        assertNull(torneo.getCategoria());
        assertNull(torneo.getFechaInicio());
        assertNull(torneo.getFechaFin());
        assertNull(torneo.getIdSec());
        assertNull(torneo.getModo());
        assertNull(torneo.getPremio());
        assertNull(torneo.getUbicacion());

    }
    @Test
    public void shouldMapTorneoFromTorneoCreateDto() {
        TorneoCreateDto torneoCreateDto = new TorneoCreateDto();
        torneoCreateDto.setCategoria(Categoria.MASTER_1000);
        torneoCreateDto.setFechaInicio("2021-10-10");
        torneoCreateDto.setFechaFin("2021-10-20");
        torneoCreateDto.setIdSec(1L);
        torneoCreateDto.setModo(Modo.INDIVIDUAL.toString());
        torneoCreateDto.setPremio(100L);
        torneoCreateDto.setUbicacion("Madrid");

        Torneo torneo = torneoMapper.toTorneo(torneoCreateDto);

        assertNotNull(torneo);
        assertEquals(torneoCreateDto.getCategoria().toString(), torneo.getCategoria().toString());
        assertEquals(torneoCreateDto.getFechaInicio(), torneo.getFechaInicio());
        assertEquals(torneoCreateDto.getFechaFin(), torneo.getFechaFin());
        assertEquals(torneoCreateDto.getIdSec(), torneo.getIdSec());
        assertEquals(torneoCreateDto.getModo().toString(), torneo.getModo().toString());
        assertEquals(torneoCreateDto.getPremio(), torneo.getPremio());
        assertEquals(torneoCreateDto.getUbicacion(), torneo.getUbicacion());

    }

}


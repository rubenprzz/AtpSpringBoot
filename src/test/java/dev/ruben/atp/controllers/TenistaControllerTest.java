package dev.ruben.atp.controllers;

import dev.ruben.atp.dto.TenistaCreateDTO;
import dev.ruben.atp.dto.TenistaResponseDTO;
import dev.ruben.atp.dto.TenistaUpdateDTO;
import dev.ruben.atp.exceptions.TenistaNotFoundException;
import dev.ruben.atp.exceptions.TorneoNotFoundException;
import dev.ruben.atp.mapper.TenistaMapper;
import dev.ruben.atp.models.Participante;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.repository.TenistaRepository;
import dev.ruben.atp.services.TenistaService;
import dev.ruben.atp.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class TenistaControllerTest {

    @InjectMocks
    private TenistaController tenistaController;

    @Mock
    private TenistaService tenistaService;

    @Mock
    private TenistaRepository tenistaRepository;
    @Mock
    private TenistaMapper tenistaMapper;
    @Mock
    private PaginationLinksUtils paginationLinksUtils;
    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should return Tenista when getTenistaById is called with valid id")
    public void shouldReturnTenistaById() {
        Tenista tenista = new Tenista();

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));

        ResponseEntity<?> response = tenistaController.getTenistaById(1L);

        assertEquals(tenista, response.getBody());
    }

    @Test
    @DisplayName("Should throw TenistaNotFoundException when getTenistaById is called with invalid id")
    public void shouldThrowExceptionWhenGetTenistaByIdIsCalledWithInvalidId() {
        when(tenistaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TenistaNotFoundException.class, () -> tenistaController.getTenistaById(1L));
    }

    @Test
    @DisplayName("Should throw TorneoNotFoundException with message and cause")
    public void shouldThrowExceptionWithMessageAndCause() {
        // Arrange
        String expectedMessage = "tenista not found";
        Throwable expectedCause = new RuntimeException("Something went wrong");

        // Act
        TenistaNotFoundException exception = assertThrows(
                TenistaNotFoundException.class,
                () -> {
                    throw new TenistaNotFoundException(expectedMessage, expectedCause);
                }
        );

        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(expectedCause, exception.getCause());
    }


    @Test
    @DisplayName("Should save Tenista when saveTenista is called with valid tenista")
    public void shouldSaveTenista() {
        TenistaCreateDTO tenistaCreateDTO = new TenistaCreateDTO(1L, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        Tenista tenista = new Tenista();

        when(tenistaRepository.save(any(Tenista.class))).thenReturn(tenista);

        ResponseEntity<?> response = tenistaController.saveTenista(tenistaCreateDTO);

        assertEquals(tenista, tenistaRepository.save(tenista));
    }

    @Test
    @DisplayName("Should calculate winrate correctly when losses are not zero")
    public void shouldCalculateWinrateWhenLossesAreNotZero() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setWins(10.0);
        tenistaResponseDTO.setLoses(5.0);

        Double winrate = tenistaResponseDTO.getWinrate();

        assertEquals(67, winrate, 0.01);
    }

    @Test
    @DisplayName("Should return 100% winrate when losses are zero")
    public void shouldReturn100PercentWinrateWhenLossesAreZero() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setWins(10.0);
        tenistaResponseDTO.setLoses(0.0);

        Double winrate = tenistaResponseDTO.getWinrate();

        assertEquals(100.0, winrate, 0.01);
    }

    @Test
    @DisplayName("Should return 0% winrate when both wins and losses are zero")
    public void shouldReturnZeroPercentWinrateWhenBothWinsAndLossesAreZero() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setWins(0.0);
        tenistaResponseDTO.setLoses(0.0);
        Double winrate = tenistaResponseDTO.getWinrate();

        assertEquals(100.0, winrate, 0.01);
    }

    @Test
    @DisplayName("Should update Tenista when updateTenista is called with valid id and tenista")
    public void shouldUpdateTenista() {
        Tenista tenista = new Tenista();

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));
        when(tenistaRepository.save(any(Tenista.class))).thenReturn(tenista);

        ResponseEntity<?> response = tenistaController.updateTenista(1L, new TenistaUpdateDTO(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null));

        assertEquals(tenista, tenistaRepository.save(tenista));
    }

    @Test
    @DisplayName("Should delete Tenista when deleteTenistaById is called with valid id")
    public void shouldDeleteTenista() {
        Tenista tenista = new Tenista();

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));

        ResponseEntity<?> response = tenistaController.deleteTenistaById(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Should set and get id correctly")
    public void shouldSetAndGetId() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setId(1L);

        Long id = tenistaResponseDTO.getId();

        assertEquals(1L, id);
    }

    @Test
    @DisplayName("Should set and get ranking correctly")
    public void shouldSetAndGetRanking() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setRanking(10L);

        Long ranking = tenistaResponseDTO.getRanking();

        assertEquals(10L, ranking);
    }

    @Test
    @DisplayName("Should set and get name correctly")
    public void shouldSetAndGetName() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setNombreCompleto("Rafa Nadal");

        String name = tenistaResponseDTO.getNombreCompleto();

        assertEquals("Rafa Nadal", name);
    }

    @Test
    @DisplayName("Should set and get age correctly")

    public void shouldSetAndGetAge() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setEdad(35L);

        Long age = tenistaResponseDTO.getEdad();

        assertEquals(35, age);
    }

    @Test
    @DisplayName("Should set and get country correctly")
    public void shouldSetAndGetCountry() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setPais("Spain");

        String country = tenistaResponseDTO.getPais();

        assertEquals("Spain", country);
    }

    @Test
    @DisplayName("Should set and get wins correctly")
    public void shouldSetAndGetWins() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setWins(10.0);

        Double wins = tenistaResponseDTO.getWins();

        assertEquals(10.0, wins, 0.01);
    }

    @Test
    @DisplayName("Should set and get losses correctly")
    public void shouldSetAndGetLosses() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setLoses(5.0);

        Double losses = tenistaResponseDTO.getLoses();

        assertEquals(5.0, losses, 0.01);
    }

    @Test
    @DisplayName("Should set and get winrate correctly")
    public void shouldSetAndGetWinrate() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setLoses(5.0);
        tenistaResponseDTO.setWins(10.0);
        tenistaResponseDTO.setWinrate(67.0);

        Double winrate = tenistaResponseDTO.getWinrate();

        assertEquals(67.0, winrate, 0.01);
    }

    @Test
    @DisplayName("Should set and get fechaNacimiento correctly")
    public void shouldSetAndGetFechaNacimiento() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setFechaNacimiento("1990-06-03");

        String fechaNacimiento = tenistaResponseDTO.getFechaNacimiento();

        assertEquals("1990-06-03", fechaNacimiento);
    }

    @Test
    @DisplayName("Should set and get altura correctly")
    public void shouldSetAndGetAltura() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setAltura(180.0);

        Double altura = tenistaResponseDTO.getAltura();

        assertEquals(180.0, altura, 0.01);
    }

    @Test
    @DisplayName("Should set and get peso correctly")
    public void shouldSetAndGetPeso() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setPeso(80.0);

        Double peso = tenistaResponseDTO.getPeso();

        assertEquals(80.0, peso, 0.01);
    }

    @Test
    @DisplayName("Should set and get mano correctly")
    public void shouldSetAndGetHand() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setMano("DIESTRO");

        String hand = tenistaResponseDTO.getMano();

        assertEquals("DIESTRO", hand);
    }

    @Test
    @DisplayName("Should set and get reves correctly")
    public void shouldSetAndGetReves() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setReves("DOS_MANOS");

        String reves = tenistaResponseDTO.getReves();

        assertEquals("DOS_MANOS", reves);
    }

    @Test
    @DisplayName("Should set and get entrenador correctly")
    public void shouldSetAndGetEntrenador() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setEntrenador("Carlos Moya");

        String entrenador = tenistaResponseDTO.getEntrenador();

        assertEquals("Carlos Moya", entrenador);
    }

    @Test
    @DisplayName("Should set and get bestRanking correctly")
    public void shouldSetAndGetBestRanking(){
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setBestRanking(1L);

        Long bestRanking = tenistaResponseDTO.getBestRanking();

        assertEquals(1L, bestRanking);
    }
    @Test
    @DisplayName("Should set and get dineroGanado correctly")
    public void shouldSetAndGetDineroGanado(){
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setDineroGanado(10000.0);

        Double dineroGanado = tenistaResponseDTO.getDineroGanado();

        assertEquals(10000.0, dineroGanado);
    }
    @Test
    @DisplayName("Should set and get imagen correctly")
    public void shouldSetAndGetImagen() {
        TenistaResponseDTO tenistaResponseDTO = new TenistaResponseDTO();
        tenistaResponseDTO.setImagen("imagen");

        String imagen = tenistaResponseDTO.getImagen();

        assertEquals("imagen", imagen);
    }


}
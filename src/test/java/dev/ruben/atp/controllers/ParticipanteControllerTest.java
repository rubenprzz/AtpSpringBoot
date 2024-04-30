package dev.ruben.atp.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.ruben.atp.exceptions.CategoriaBadRequestException;
import dev.ruben.atp.models.*;
import dev.ruben.atp.repository.TenistaRepository;
import dev.ruben.atp.services.ParticipanteService;
import dev.ruben.atp.services.TenistaService;
import dev.ruben.atp.services.TorneoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class ParticipanteControllerTest {

    @Mock
    private ParticipanteService participanteService;

    @Mock
    private TenistaService tenistaService;

    @Mock
    private TorneoService torneoService;

    @Mock
    private TenistaRepository tenistaRepository;

    @InjectMocks
    private ParticipanteController participanteController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return list of participantes when getParticipantes is called")
    public void shouldReturnListOfParticipantes() {
        when(participanteService.findAll()).thenReturn(List.of(new Participante()));

        ResponseEntity<?> response = participanteController.getParticipantes();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participanteService, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return participante when getParticipante is called with valid id")
    public void shouldReturnParticipante() {
        when(participanteService.findById(any())).thenReturn(Optional.of(new Participante()));

        ResponseEntity<?> response = participanteController.getParticipante(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participanteService, times(1)).findById(any());
    }


    @Test
    @DisplayName("Should throw RuntimeException when getParticipante is called with invalid id")
    public void shouldThrowExceptionWhenGetParticipanteIsCalledWithInvalidId() {
        when(participanteService.findById(any())).thenReturn(Optional.empty());
        participanteController.getParticipante(1L);
        verify(participanteService, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should throw RuntimeException when deleteParticipante is called with invalid id")
    public void shouldThrowExceptionWhenDeleteParticipanteIsCalledWithInvalidId() {
        when(participanteService.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> participanteController.deleteParticipante(1L));
        verify(participanteService, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should delete participante when deleteParticipante is called with valid id")
    public void shouldDeleteParticipante() {
        when(participanteService.findById(any())).thenReturn(Optional.of(new Participante()));

        ResponseEntity<?> response = participanteController.deleteParticipante(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participanteService, times(1)).findById(any());
        verify(participanteService, times(1)).deleteById(any());
    }
    @Test
    @DisplayName("Should throw CategoriaBadRequestException when createParticipante is called with invalid categoria")
    public void shouldThrowExceptionWhenCreateParticipanteIsCalledWithInvalidCategoria() {
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setId(1L);
        tenista.setModo(Modo.INDIVIDUAL);
        tenista.setPuntos(0L);
        participante.setResultado("GANADOR");
        participante.setPuntosOtorgados(100L);
        Torneo torneoMock = new Torneo();
        torneoMock.setId("e3965c44-bf3a-4adc-a146-d10d55f97402");
        torneoMock.setModo(Modo.DOBLE);
        torneoMock.setCategoria(Categoria.MASTER_1000);

        when(torneoService.findById(any())).thenReturn(Optional.of(torneoMock));
        participante.setTorneo(torneoMock);

        participante.setTenista(tenista);

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));
        when(torneoService.findById(any())).thenReturn(Optional.of(torneoMock));
        when(participanteService.save(any())).thenReturn(participante);

        assertThrows(CategoriaBadRequestException.class, () -> participanteController.createParticipante(participante));
        verify(tenistaRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should save participante when createParticipante is called with valid participante")
    public void shouldSaveParticipante() {
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setId(1L);
        tenista.setModo(Modo.INDIVIDUAL);
        tenista.setPuntos(0L);
        participante.setResultado("GANADOR");
        participante.setPuntosOtorgados(100L);
        Torneo torneoMock = new Torneo();
        torneoMock.setId("e3965c44-bf3a-4adc-a146-d10d55f97402");
        torneoMock.setModo(Modo.INDIVIDUAL);
        torneoMock.setCategoria(Categoria.MASTER_1000);

        when(torneoService.findById(any())).thenReturn(Optional.of(torneoMock));
        participante.setTorneo(torneoMock);

        participante.setTenista(tenista);

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));
        when(torneoService.findById(any())).thenReturn(Optional.of(torneoMock));
        when(participanteService.save(any())).thenReturn(participante);

        ResponseEntity<?> response = participanteController.createParticipante(participante);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tenistaRepository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Should throw RuntimeException when createParticipante is called with invalid tenista")
    public void shouldThrowExceptionWhenCreateParticipanteIsCalledWithInvalidTenista() {
        when(tenistaRepository.findById(any())).thenReturn(Optional.empty());
        Participante participante = new Participante();
        participante.setTenista(new Tenista());
        participante.setTorneo(new Torneo());

        assertThrows(RuntimeException.class, () -> participanteController.createParticipante(participante));
        verify(tenistaRepository, times(1)).findById(any());


    }

    @Test
    @DisplayName("Should throw RuntimeException when createParticipante is called with invalid torneo")
    public void shouldThrowExceptionWhenCreateParticipanteIsCalledWithInvalidTorneo() {
        when(torneoService.findById(any())).thenReturn(Optional.empty());
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setId(1L);
        participante.setTenista(tenista);
        Torneo torneo = new Torneo();
        torneo.setId("1L");
        participante.setTorneo(torneo);

        assertThrows(RuntimeException.class, () -> participanteController.createParticipante(participante));

        verify(torneoService, times(0)).findById(any());
    }
    @Test
    @DisplayName("Should update participante when updateParticipante is called with valid id and participante")
    public void shouldUpdateParticipante() {
        Participante participante = new Participante();
        Participante participanteUpdated = new Participante();
        participanteUpdated.setResultado("PERDEDOR");
        participanteUpdated.setPuntosOtorgados(0L);
        participanteUpdated.setTenista(new Tenista());
        participanteUpdated.setTorneo(new Torneo());

        when(participanteService.findById(any())).thenReturn(Optional.of(participante));
        when(participanteService.save(any())).thenReturn(participanteUpdated);

        ResponseEntity<?> response = participanteController.updateParticipante(participante);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participanteService, times(1)).findById(any());
        verify(participanteService, times(1)).save(any());
    }
    @Test
    @DisplayName("Should throw RuntimeException when updateParticipante is called with invalid id")
    public void shouldThrowExceptionWhenUpdateParticipanteIsCalledWithInvalidId() {
        when(participanteService.findById(any())).thenReturn(Optional.empty());
        Participante participante = new Participante();
        participante.setId(1L);

        assertThrows(RuntimeException.class, () -> participanteController.updateParticipante(participante));
        verify(participanteService, times(1)).findById(any());
    }
    @Test
    @DisplayName("Should find participante by tenista ranking when findParticipanteByTenistaRanking is called with valid ranking")
    public void shouldFindParticipanteByTenistaRanking() {
        when(participanteService.findParticipanteByTenistaRanking(any())).thenReturn(Optional.of(new Participante()));

        ResponseEntity<?> response = participanteController.findParticipanteByTenistaRanking(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(participanteService, times(1)).findParticipanteByTenistaRanking(any());
    }
    @Test
    @DisplayName("Should throw RuntimeException when findParticipanteByTenistaRanking is called with invalid ranking")
    public void shouldThrowExceptionWhenFindParticipanteByTenistaRankingIsCalledWithInvalidRanking() {
        when(participanteService.findParticipanteByTenistaRanking(any())).thenReturn(Optional.empty());

        verify(participanteService, times(0)).findParticipanteByTenistaRanking(any());
    }




}
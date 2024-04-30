package dev.ruben.atp.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.ruben.atp.exceptions.CategoriaBadRequestException;
import dev.ruben.atp.models.Participante;
import dev.ruben.atp.models.Tenista;
import dev.ruben.atp.models.Torneo;
import dev.ruben.atp.repository.ParticipanteRepository;
import dev.ruben.atp.repository.TenistaRepository;
import dev.ruben.atp.services.ParticipanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParticipanteServiceTest {

    @Mock
    private ParticipanteRepository participanteRepository;

    @Mock
    private TenistaRepository tenistaRepository;

    @InjectMocks
    private ParticipanteService participanteService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save Participante and update Tenista points when saveParticipante is called")
    public void shouldSaveParticipanteAndUpdateTenistaPoints() {
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setPuntos(100L);
        participante.setTenista(tenista);
        participante.setPuntosOtorgados(50L);

        when(tenistaRepository.findById(any())).thenReturn(Optional.of(tenista));
        when(participanteRepository.save(any())).thenReturn(participante);

        Participante result = participanteService.saveParticipante(participante);

        assertEquals(150L, result.getTenista().getPuntos());
        verify(tenistaRepository, times(1)).save(any());
    }
    @Test
    @DisplayName("Should throw CategoriaBadRequestException when saveParticipante is called with invalid Tenista id")
    public void shouldThrowCategoriaBadRequestExceptionWhenSaveParticipanteIsCalledWithInvalidTenistaId() {
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setPuntos(100L);
        participante.setTenista(tenista);
        participante.setPuntosOtorgados(50L);

        when(tenistaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> participanteService.saveParticipante(participante));
        verify(tenistaRepository, times(0)).save(any());
    }
    @Test
    public void shouldReturnAllParticipants() {
        Participante participante1 = new Participante();
        Participante participante2 = new Participante();

        when(participanteRepository.findAll()).thenReturn(Arrays.asList(participante1, participante2));

        List<Participante> participantes = participanteRepository.findAll();

        assertEquals(2, participantes.size());
        assertEquals(participante1, participantes.get(0));
        assertEquals(participante2, participantes.get(1));
    }


    @Test
    @DisplayName("Should return Participantes by Torneo id when findAllByTorneoIdSec is called")
    public void shouldReturnParticipantesByTorneoIdWhenFindAllByTorneoIdSecIsCalled() {
        Participante participante = new Participante();
        Torneo torneo = new Torneo();
        torneo.setIdSec(1L);
        participante.setTorneo(torneo);

        when(participanteRepository.findAllByTorneoIdSec(any(), any())).thenReturn(new PageImpl<>(Arrays.asList(participante)));

        Page<Participante> result = participanteService.findAllByTorneoIdSec(1L, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
        assertEquals(1L, result.getContent().get(0).getTorneo().getIdSec());
        verify(participanteRepository, times(1)).findAllByTorneoIdSec(any(), any());
    }

    @Test
    @DisplayName("Should return Participante by Tenista ranking when findParticipanteByTenistaRanking is called")
    public void shouldReturnParticipanteByTenistaRankingWhenFindParticipanteByTenistaRankingIsCalled() {
        Participante participante = new Participante();
        Tenista tenista = new Tenista();
        tenista.setRanking(1L);
        participante.setTenista(tenista);

        when(participanteRepository.findParticipanteByTenistaRanking(any())).thenReturn(Optional.of(participante));

        Optional<Participante> result = participanteService.findParticipanteByTenistaRanking(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getTenista().getRanking());
        verify(participanteRepository, times(1)).findParticipanteByTenistaRanking(any());
    }

}